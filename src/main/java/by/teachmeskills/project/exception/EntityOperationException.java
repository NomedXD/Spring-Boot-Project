package by.teachmeskills.project.exception;

import java.sql.SQLException;

public class EntityOperationException extends SQLException {
    public EntityOperationException(String message) {
        super(message);
    }
}
