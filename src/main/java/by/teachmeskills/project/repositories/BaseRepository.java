package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.BaseEntity;
import by.teachmeskills.project.exception.EntityOperationException;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {
    ConnectionPool connectionPool = ConnectionPool.getInstance();

    T create(T entity) throws EntityOperationException;

    List<T> read() throws EntityOperationException;

    T update(T entity) throws EntityOperationException;

    void delete(int id) throws EntityOperationException;
}
