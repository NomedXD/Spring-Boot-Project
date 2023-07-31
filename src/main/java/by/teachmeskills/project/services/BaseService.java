package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.BaseEntity;
import by.teachmeskills.project.exception.SQLExecutionException;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    T create(T entity) throws SQLExecutionException;

    List<T> read() throws SQLExecutionException;

    T update(T entity) throws SQLExecutionException;

    void delete(int id) throws SQLExecutionException;
}
