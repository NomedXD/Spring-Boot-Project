package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.exception.EntityOperationException;
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
    public Category create(Category entity) throws EntityOperationException {
        return categoryRepository.create(entity);
    }

    @Override
    public List<Category> read() throws EntityOperationException {
        return categoryRepository.read();
    }

    @Override
    public Category update(Category entity) throws EntityOperationException {
        return categoryRepository.update(entity);
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        categoryRepository.delete(id);
    }

    @Override
    public void getCategoryByName(String name) throws EntityOperationException {
        categoryRepository.getCategoryByName(name);
    }
}
