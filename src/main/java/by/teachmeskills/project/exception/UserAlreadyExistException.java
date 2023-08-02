package by.teachmeskills.project.exception;

import java.sql.SQLIntegrityConstraintViolationException;

public class UserAlreadyExistException extends IllegalArgumentException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
