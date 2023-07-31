package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.exception.SQLExecutionException;
import by.teachmeskills.project.repositories.ProductRepository;
import by.teachmeskills.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product entity) throws SQLExecutionException {
        return productRepository.create(entity);
    }

    @Override
    public List<Product> read() throws SQLExecutionException {
        return productRepository.read();
    }

    @Override
    public Product update(Product entity) throws SQLExecutionException {
        return productRepository.update(entity);
    }

    @Override
    public void delete(int id) throws SQLExecutionException {
        productRepository.delete(id);
    }

    @Override
    public List<Product> getCategoryProducts(int categoryId) {
        return productRepository.getCategoryProducts(categoryId);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.getProductByName(name);
    }

    @Override
    public List<Product> getSearchedProducts(String searchString) {
        return productRepository.getSearchedProducts(searchString);
    }
}
