package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.EntityOperationException;

public interface CategoryService extends BaseService<Category>{
    void getCategoryByName(String name) throws EntityOperationException;

    Category getCategoryById(Integer id) throws EntityOperationException;
}
