package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.exception.SQLExecutionException;

public interface CategoryRepository extends BaseRepository<Category>{
    Category getCategoryByName(String name) throws SQLExecutionException;
}
