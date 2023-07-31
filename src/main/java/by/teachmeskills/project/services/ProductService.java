package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.exception.SQLExecutionException;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    List<Product> getCategoryProducts(int categoryId) throws SQLExecutionException;

    Product getProductById(int id) throws SQLExecutionException;

    Product getProductByName(String name) throws SQLExecutionException;

    List<Product> getSearchedProducts(String searchString) throws SQLExecutionException;
}
