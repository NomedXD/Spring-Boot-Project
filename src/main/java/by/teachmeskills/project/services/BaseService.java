package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.BaseEntity;
import by.teachmeskills.project.exception.EntityOperationException;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    T create(T entity) throws EntityOperationException;

    List<T> read() throws EntityOperationException;

    T update(T entity) throws EntityOperationException;

    void delete(int id) throws EntityOperationException;
}
