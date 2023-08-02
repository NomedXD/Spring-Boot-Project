package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.exception.EntityOperationException;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> getCategoryProducts(int categoryId) throws EntityOperationException;

    Product getProductById(int id) throws EntityOperationException;

    Product getProductByName(String name) throws EntityOperationException;

    List<Product> getSearchedProducts(String searchString) throws EntityOperationException;
}
