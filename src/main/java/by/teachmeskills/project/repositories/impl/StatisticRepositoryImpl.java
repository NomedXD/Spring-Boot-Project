package by.teachmeskills.project.repositories.impl;

import by.teachmeskills.project.domain.StatisticEntity;
import by.teachmeskills.project.exception.SQLExecutionException;
import by.teachmeskills.project.repositories.StatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticRepositoryImpl implements StatisticRepository {
    private final static Logger logger = LoggerFactory.getLogger(StatisticRepositoryImpl.class);
    private static final String CREATE_STATISTIC_DATA = "INSERT INTO time_statistic (description) VALUES (?)";
    private static final String GET_ALL_STATISTIC = "SELECT * FROM time_statistic";
    private static final String UPDATE_STATISTIC_DATA = "UPDATE time_statistic SET description = ? WHERE id = ?";
    private static final String DELETE_STATISTIC = "DELETE FROM time_statistic WHERE id = ?";
    private static final String GET_STATISTIC_BY_ITS_ID = "SELECT * FROM time_statistic WHERE id = ?";

    @Override
    public StatisticEntity create(StatisticEntity entity) throws SQLExecutionException {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_STATISTIC_DATA);
            preparedStatement.setString(1, entity.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("SQLException while creating statistic note in db. Most likely request is wrong");
            throw new SQLExecutionException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
        return entity;
    }

    @Override
    public List<StatisticEntity> read() throws SQLExecutionException {
        List<StatisticEntity> statisticEntityList = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_STATISTIC);
            while (resultSet.next()) {
                statisticEntityList.add(new StatisticEntity(resultSet.getInt("id"), resultSet.getString("description")));
            }
        } catch (SQLException e) {
            logger.warn("SQLException while getting all statistic. Most likely request is wrong");
            throw new SQLExecutionException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
        return statisticEntityList;
    }

    @Override
    public StatisticEntity update(StatisticEntity entity) throws SQLExecutionException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_STATISTIC_DATA);
            preparedStatement.setString(1, entity.getDescription());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
            entity = getStatisticEntityById(entity.getId());
            return entity;
        } catch (SQLException e) {
            logger.warn("SQLException while updating statistic. Most likely request is wrong");
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
            preparedStatement = connection.prepareStatement(DELETE_STATISTIC);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("SQLException while deleting statistic. Most likely request is wrong");
            throw new SQLExecutionException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public StatisticEntity getStatisticEntityById(int id) throws SQLExecutionException {
        StatisticEntity statisticEntity = new StatisticEntity();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_STATISTIC_BY_ITS_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                statisticEntity = new StatisticEntity(resultSet.getInt("id"), resultSet.getString("description"));
            }
            return statisticEntity;
        } catch (SQLException e) {
            logger.warn("SQLException while getting statistic by it's id. Most likely request is wrong");
            throw new SQLExecutionException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }
}
