package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Cart;
import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.domain.Search;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.exception.EntityOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

public interface ProductService extends BaseService<Product> {
    List<Product> getCategoryProducts(int categoryId) throws EntityOperationException;

    Optional<Product> getProductById(int id) throws EntityOperationException;

    ModelAndView getSearchedPaginatedProducts(Search search, Integer currentPage) throws EntityOperationException;

    ModelAndView getPaginatedProductsByCategoryId(Integer categoryId, Integer currentPage, Integer pageSize);

    Long getCountOfAllProducts() throws EntityOperationException;

    Long getCountAppropriateProducts(Search search) throws EntityOperationException;

    ModelAndView applyProductsQuantity(Cart cart, HttpServletRequest request);

    void exportCategoryProducts(Integer categoryId, HttpServletResponse response) throws CSVExportException;

    ModelAndView importCategoryProducts(MultipartFile file, Integer categoryId) throws CSVImportException;
}
