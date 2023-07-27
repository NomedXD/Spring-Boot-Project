package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Category;

public interface CategoryRepository extends BaseRepository<Category>{
    Category getCategoryByName(String name);
}
