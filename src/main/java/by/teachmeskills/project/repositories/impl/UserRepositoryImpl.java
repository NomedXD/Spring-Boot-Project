package by.teachmeskills.project.repositories.impl;

import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.SQLExecutionException;
import by.teachmeskills.project.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final static Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final String GET_USER_QUERY = "SELECT * FROM users WHERE mail = ? AND password = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String REGISTER_USER = "INSERT INTO users(mail, password, name, surname, date," +
            " balance) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_USER_DATA = "UPDATE users SET mobile = ?, street = ?, accommodation_number = ?, flat_number = ? WHERE id = ?";

    @Override
    public User create(User entity) throws SQLExecutionException {
        User user;
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_USER);
            preparedStatement.setString(1, entity.getMail());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getName());
            preparedStatement.setString(4, entity.getSurname());
            preparedStatement.setDate(5, Date.valueOf(entity.getDate()));
            preparedStatement.setFloat(6, entity.getCurrentBalance());
            preparedStatement.execute();
            user = getUserByCredentials(entity.getMail(), entity.getPassword());
            return user;
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                return null;
            } else {
                logger.warn("SQLException while saving user. Most likely request is wrong");
                throw new SQLExecutionException("Unexpected error on the site. How do you get here?\nCheck us later");
            }
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public List<User> read() throws SQLExecutionException {
        List<User> userArrayList = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS);
            while (resultSet.next()) {
                userArrayList.add(new User(resultSet.getInt("id"), resultSet.getString("mail"), resultSet.getString("password"),
                        resultSet.getString("name"), resultSet.getString("surname"), resultSet.getDate("date").toLocalDate(),
                        resultSet.getFloat("balance"), resultSet.getString("mobile"),
                        resultSet.getString("street"), resultSet.getString("accommodation_number"),
                        resultSet.getString("flat_number")));
            }
            return userArrayList;
        } catch (SQLException e) {
            logger.warn("SQLException while getting users. Most likely request is wrong");
            throw new SQLExecutionException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public User update(User entity) throws SQLExecutionException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER_DATA);
            preparedStatement.setString(1, entity.getMobile());
            preparedStatement.setString(2, entity.getStreet());
            preparedStatement.setString(3, entity.getAccommodationNumber());
            preparedStatement.setString(4, entity.getFlatNumber());
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.executeUpdate();
            entity = getUserByCredentials(entity.getMail(), entity.getPassword());
            return entity;
        } catch (SQLException e) {
            logger.warn("SQLException while saving user. Most likely request is wrong");
            throw new SQLExecutionException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public void delete(int id) throws SQLExecutionException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("SQLException while deleting user. Most likely request is wrong");
            throw new SQLExecutionException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public User getUserByCredentials(String mail, String password) throws SQLExecutionException {
        User user = null;
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_QUERY);
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("mail"), resultSet.getString("password"),
                        resultSet.getString("name"), resultSet.getString("surname"), resultSet.getDate("date").toLocalDate(),
                        resultSet.getFloat("balance"), resultSet.getString("mobile"),
                        resultSet.getString("street"), resultSet.getString("accommodation_number"),
                        resultSet.getString("flat_number"));
            }
            return user;
        } catch (SQLException e) {
            logger.warn("SQLException while getting user. Most likely request is wrong");
            throw new SQLExecutionException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }
}
