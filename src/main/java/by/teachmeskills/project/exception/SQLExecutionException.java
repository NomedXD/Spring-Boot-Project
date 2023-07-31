package by.teachmeskills.project.exception;

import java.sql.SQLException;

public class SQLExecutionException extends SQLException {
    public SQLExecutionException(String message) {
        super(message);
    }
}
