package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.exception.EntityOperationException;
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
    public Product create(Product entity) throws EntityOperationException {
        return productRepository.create(entity);
    }

    @Override
    public List<Product> read() throws EntityOperationException {
        return productRepository.read();
    }

    @Override
    public Product update(Product entity) throws EntityOperationException {
        return productRepository.update(entity);
    }

    @Override
    public void delete(int id) throws EntityOperationException {
        productRepository.delete(id);
    }

    @Override
    public List<Product> getCategoryProducts(int categoryId) throws EntityOperationException {
        return productRepository.getCategoryProducts(categoryId);
    }

    @Override
    public Product getProductById(int id) throws EntityOperationException {
        return productRepository.getProductById(id);
    }

    @Override
    public Product getProductByName(String name) throws EntityOperationException {
        return productRepository.getProductByName(name);
    }

    @Override
    public List<Product> getSearchedProducts(String searchString) throws EntityOperationException {
        return productRepository.getSearchedProducts(searchString);
    }
}
