package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Product;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> getCategoryProducts(int categoryId);

    Product getProductById(int id);

    Product getProductByName(String name);

    List<Product> getSearchedProducts(String searchString);
}
