package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.BaseEntity;
import by.teachmeskills.project.exception.SQLExecutionException;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {
    ConnectionPool connectionPool = ConnectionPool.getInstance();

    T create(T entity) throws SQLExecutionException;

    List<T> read() throws SQLExecutionException;

    T update(T entity) throws SQLExecutionException;

    void delete(int id) throws SQLExecutionException;
}
