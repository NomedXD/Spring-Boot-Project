package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.exception.SQLExecutionException;

public interface CategoryService extends BaseService<Category>{
    void getCategoryByName(String name) throws SQLExecutionException;
}
