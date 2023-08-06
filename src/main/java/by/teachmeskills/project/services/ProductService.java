package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Cart;
import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.domain.SearchEntity;
import by.teachmeskills.project.exception.EntityOperationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    List<Product> getCategoryProducts(int categoryId) throws EntityOperationException;

    Product getProductById(int id) throws EntityOperationException;

    ModelAndView getSearchedProducts(SearchEntity searchEntity, Integer currentPage) throws EntityOperationException;

    Long getCountOfAllProducts() throws EntityOperationException;

    Long getCountAppropriateProducts(SearchEntity searchEntity) throws EntityOperationException;

    ModelAndView applyProductsQuantity(Cart cart, HttpServletRequest request);
}
