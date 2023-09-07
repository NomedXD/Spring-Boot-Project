package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    T create(T entity);

    List<T> read();

    T update(T entity);

    void delete(Integer id);
}
