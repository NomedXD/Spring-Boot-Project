package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.domain.Search;
import by.teachmeskills.project.exception.EntityOperationException;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> getCategoryProducts(Integer categoryId) throws EntityOperationException;

    Product getProductById(Integer id) throws EntityOperationException;

    List<Product> getSearchedProducts(Search search, Integer integer, Integer count) throws EntityOperationException;

    Long getCountOfAllProducts() throws EntityOperationException;
    Long getCountAppropriateProducts(Search search) throws EntityOperationException;

    List<Product> readOrderedByNameInRange(Integer first, Integer count) throws EntityOperationException;
}
