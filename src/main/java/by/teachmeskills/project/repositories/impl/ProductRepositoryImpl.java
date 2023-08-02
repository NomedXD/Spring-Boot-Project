package by.teachmeskills.project.repositories.impl;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.ProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {
    private final static Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);
    private static final String CREATE_PRODUCT = "INSERT INTO products(name, imagepath, description, categoryid, price) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String UPDATE_PRODUCT_DATA = "UPDATE products SET name = ?, imagepath = ?, description = ?, categoryid = ?, price = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";
    private static final String GET_PRODUCTS_BY_CATEGORY_ID = "SELECT * FROM products WHERE categoryid = ?";
    private static final String GET_PRODUCT_BY_ITS_ID = "SELECT * FROM products WHERE id = ?";
    private static final String GET_PRODUCT_BY_ITS_NAME = "SELECT * FROM products WHERE name = ?";
    private static final String GET_SEARCHED_PRODUCTS = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ?";

    @Override
    public Product create(Product entity) throws EntityOperationException {
        Product product = new Product();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PRODUCT);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getImagepath());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setInt(4, entity.getCategoryid());
            preparedStatement.setFloat(5, entity.getPrice());
            preparedStatement.execute();
            product = getProductByName(entity.getName());
            return product;
        } catch (SQLException e) {
            logger.warn("SQLException while creating category. Most likely request is wrong. Full message:" + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public List<Product> read() throws EntityOperationException {
        List<Product> productArrayList = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_PRODUCTS);
            while (resultSet.next()) {
                productArrayList.add(new Product(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("imagepath"), resultSet.getString("description"),
                        resultSet.getInt("categoryid"), resultSet.getFloat("price")));
            }
        } catch (SQLException e) {
            logger.warn("SQLException while creating category. Most likely request is wrong. Full message:" + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
        return productArrayList;
    }

    @Override
    public Product update(Product entity) throws EntityOperationException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_DATA);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getImagepath());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setInt(4, entity.getCategoryid());
            preparedStatement.setFloat(5, entity.getPrice());
            preparedStatement.setInt(6, entity.getId());
            preparedStatement.executeUpdate();
            entity = getProductById(entity.getId());
            return entity;
        } catch (SQLException e) {
            logger.warn("SQLException while creating category. Most likely request is wrong. Full message:" + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public void delete(int id) throws EntityOperationException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(DELETE_PRODUCT);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("SQLException while creating category. Most likely request is wrong. Full message:" + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public List<Product> getCategoryProducts(int categoryId) throws EntityOperationException {
        List<Product> productList = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCTS_BY_CATEGORY_ID);
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(new Product(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("imagepath"), resultSet.getString("description"),
                        resultSet.getInt("categoryid"), resultSet.getFloat("price")));
            }
            return productList;
        } catch (SQLException e) {
            logger.warn("SQLException while creating category. Most likely request is wrong. Full message:" + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public Product getProductById(int id) throws EntityOperationException {
        Product product = new Product();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ITS_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("imagepath"), resultSet.getString("description"),
                        resultSet.getInt("categoryid"), resultSet.getFloat("price"));
            }
            return product;
        } catch (SQLException e) {
            logger.warn("SQLException while creating category. Most likely request is wrong. Full message:" + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public Product getProductByName(String name) throws EntityOperationException {
        Product product = new Product();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ITS_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("imagepath"), resultSet.getString("description"),
                        resultSet.getInt("categoryid"), resultSet.getFloat("price"));
            }
            return product;
        } catch (SQLException e) {
            logger.warn("SQLException while creating category. Most likely request is wrong. Full message:" + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public List<Product> getSearchedProducts(String searchString) throws EntityOperationException {
        List<Product> productArrayList = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SEARCHED_PRODUCTS);
            preparedStatement.setString(1, "%" + searchString + "%");
            preparedStatement.setString(2, "%" + searchString + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productArrayList.add(new Product(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("imagepath"), resultSet.getString("description"),
                        resultSet.getInt("categoryid"), resultSet.getFloat("price")));
            }
        } catch (SQLException e) {
            logger.warn("SQLException while creating category. Most likely request is wrong. Full message:" + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        } finally {
            connectionPool.closeConnection(connection);
        }
        return productArrayList;
    }
}
