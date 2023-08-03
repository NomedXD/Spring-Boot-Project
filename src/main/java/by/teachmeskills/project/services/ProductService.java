package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.exception.EntityOperationException;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    List<Product> getCategoryProducts(int categoryId) throws EntityOperationException;

    Product getProductById(int id) throws EntityOperationException;

    List<Product> getSearchedProducts(String searchString) throws EntityOperationException;
}
