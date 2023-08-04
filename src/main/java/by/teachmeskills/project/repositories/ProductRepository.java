package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.domain.SearchEntity;
import by.teachmeskills.project.exception.EntityOperationException;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> getCategoryProducts(Integer categoryId) throws EntityOperationException;

    Product getProductById(Integer id) throws EntityOperationException;

    List<Product> getSearchedProducts(SearchEntity searchEntity, Integer integer, Integer count) throws EntityOperationException;

    Long getCountOfAllProducts() throws EntityOperationException;
    Long getCountAppropriateProducts(SearchEntity searchEntity) throws EntityOperationException;

    List<Product> getAllOrderedProducts(Integer first, Integer count) throws EntityOperationException;
}
