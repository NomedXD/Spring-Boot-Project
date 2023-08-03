package by.teachmeskills.project.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private final static Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private static volatile ConnectionPool instance;

    private static final String DB_PROPERTY_FILE = "application";
    private static final String DB_URL = "spring.datasource.url";
    private static final String DB_LOGIN = "spring.datasource.username";
    private static final String DB_PASS = "spring.datasource.password";
    private static final int MAX_CONNECTION_COUNT = 10;
    private static final int MIN_CONNECTION_COUNT = 5;

    private static final String url;
    private static final String login;
    private static final String pass;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTY_FILE);
        url = resourceBundle.getString(DB_URL);
        login = resourceBundle.getString(DB_LOGIN);
        pass = resourceBundle.getString(DB_PASS);
    }

    private volatile int currentConnectionNumber = MIN_CONNECTION_COUNT;
    private final BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(MAX_CONNECTION_COUNT, true);

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }

        return instance;
    }

    private ConnectionPool() {

        for (int i = 0; i < MIN_CONNECTION_COUNT; i++) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                pool.add(DriverManager.getConnection(url, login, pass));
            } catch (SQLException | ClassNotFoundException e) {
                logger.error("Error while getting initial connections for connection-pool");
                e.printStackTrace();
            }
        }
    }

    private void openAdditionalConnection() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            pool.add(DriverManager.getConnection(url, login, pass));
            currentConnectionNumber++;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error while opening additional connection in connection-pool");
            throw new Exception("New connection wasn't add in the connection pool", e);
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            if (pool.isEmpty() && currentConnectionNumber < MAX_CONNECTION_COUNT) {
                openAdditionalConnection();
            }
            connection = pool.take();
        } catch (Exception ex) {
            logger.warn("Max count of connections was reached. Thread interrupted");
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            if (currentConnectionNumber > MIN_CONNECTION_COUNT) {
                currentConnectionNumber--;
            }
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                logger.warn("Connection wasn't returned into connection-pool properly");
                Thread.currentThread().interrupt();
            }
        }
    }

    public void disconnect() {
        pool.forEach(s -> {
            try {
                s.close();
            } catch (SQLException e) {
                logger.error("Cannot disconnect pool connection");
            }
        });
        logger.info("DB connections were closed");
    }
}
