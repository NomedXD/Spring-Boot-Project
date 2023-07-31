package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.exception.SQLExecutionException;
import by.teachmeskills.project.repositories.CategoryRepository;
import by.teachmeskills.project.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category entity) throws SQLExecutionException {
        return categoryRepository.create(entity);
    }

    @Override
    public List<Category> read() throws SQLExecutionException {
        return categoryRepository.read();
    }

    @Override
    public Category update(Category entity) throws SQLExecutionException {
        return categoryRepository.update(entity);
    }

    @Override
    public void delete(int id) throws SQLExecutionException {
        categoryRepository.delete(id);
    }

    @Override
    public void getCategoryByName(String name) throws SQLExecutionException {
        categoryRepository.getCategoryByName(name);
    }
}
