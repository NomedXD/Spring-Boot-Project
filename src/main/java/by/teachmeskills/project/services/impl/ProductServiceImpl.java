package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.ProductRepository;
import by.teachmeskills.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

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
    public List<Product> getSearchedProducts(String searchString) throws EntityOperationException {
        return productRepository.getSearchedProducts(searchString);
    }

    @Override
    public Long getCountOfProducts() throws EntityOperationException {
        return productRepository.getCountOfAllProducts();
    }

    @Override
    public ModelAndView getProductsInRange(Integer currentPage) throws EntityOperationException {
        Long countOfAllProducts = productRepository.getCountOfAllProducts();
        List<Product> productList = productRepository.getProductsInRange((currentPage - 1) * EshopConstants.PAGE_SIZE, EshopConstants.PAGE_SIZE);
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.TOTAL_SEARCH_RESULTS.getValue(), countOfAllProducts);
        model.addAttribute(RequestParamsEnum.CURRENT_PAGE.getValue(), currentPage);
        model.addAttribute(RequestParamsEnum.TOTAL_PAGINATED_VISIBLE_PAGES.getValue(), EshopConstants.TOTAL_PAGINATED_VISIBLE_PAGES);
        model.addAttribute(RequestParamsEnum.LAST_PAGE_NUMBER.getValue(), Math.ceil(countOfAllProducts / EshopConstants.PAGE_SIZE.doubleValue()));
        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), productList);
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath(), model);
    }
}
