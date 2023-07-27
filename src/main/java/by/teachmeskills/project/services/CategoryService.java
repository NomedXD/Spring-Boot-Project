package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Category;

public interface CategoryService extends BaseService<Category>{
    Category getCategoryByName(String name);
}
