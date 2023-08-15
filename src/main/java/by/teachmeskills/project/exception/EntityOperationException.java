package by.teachmeskills.project.exception;

import jakarta.persistence.PersistenceException;
import lombok.Getter;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;

@Getter
public class EntityOperationException extends HibernateException {
    private PersistenceException exception;

    public EntityOperationException(String message) {
        super(message);
    }

    public EntityOperationException(String message, PersistenceException exception) {
        super(message);
        this.exception = exception;
    }
}
