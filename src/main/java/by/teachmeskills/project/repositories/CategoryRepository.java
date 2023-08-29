package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.exception.EntityOperationException;

public interface CategoryRepository extends BaseRepository<Category>{
    Category getCategoryByName(String name) throws EntityOperationException;

    Category getCategoryById(Integer id) throws EntityOperationException;
}
