package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Product;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    List<Product> getCategoryProducts(int categoryId);

    Product getProductById(int id);

    Product getProductByName(String name);

    List<Product> getSearchedProducts(String searchString);
}
