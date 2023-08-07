package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Cart;
import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.domain.Search;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.ProductRepository;
import by.teachmeskills.project.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
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
    public void delete(Integer id) throws EntityOperationException {
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
    public Long getCountOfAllProducts() throws EntityOperationException {
        return productRepository.getCountOfAllProducts();
    }

    @Override
    public Long getCountAppropriateProducts(Search search) throws EntityOperationException {
        return productRepository.getCountAppropriateProducts(search);
    }

    @Override
    public ModelAndView getPaginatedProducts(Search search, Integer currentPage) throws EntityOperationException {
        ModelMap model = new ModelMap();
        Long count;
        List<Product> productList;
        if ((search == null) || (search.getSearchString() == null)) {
            count = getCountOfAllProducts();
            productList = productRepository.readOrderedByNameInRange((currentPage - 1) * EshopConstants.PAGE_SIZE, EshopConstants.PAGE_SIZE);
        } else {
            count = getCountAppropriateProducts(search);
            productList = productRepository.getSearchedProducts(search, (currentPage - 1) * EshopConstants.PAGE_SIZE, EshopConstants.PAGE_SIZE);
            model.addAttribute(EshopConstants.SEARCH_ENTITY, search);
        }
        model.addAttribute(RequestParamsEnum.TOTAL_SEARCH_RESULTS.getValue(), count);
        model.addAttribute(RequestParamsEnum.CURRENT_PAGE.getValue(), currentPage);
        model.addAttribute(RequestParamsEnum.TOTAL_PAGINATED_VISIBLE_PAGES.getValue(), EshopConstants.TOTAL_PAGINATED_VISIBLE_PAGES);
        model.addAttribute(RequestParamsEnum.LAST_PAGE_NUMBER.getValue(), Math.ceil(count / EshopConstants.PAGE_SIZE.doubleValue()));
        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), productList);
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath(), model);
    }

    /*
      Внедрение конкретно HttpServletRequest, так как заранее неизвестно, сколько будет параметров в post запросе
     */
    @Override
    public ModelAndView applyProductsQuantity(Cart cart, HttpServletRequest request) {
        for (Product product: cart.getProducts()) {
            String quantity = request.getParameter(product.getId()+"quantity");
            if(quantity!= null) {
                Integer currentQuantity = cart.getProductQuantities().get(product.getId());
                cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * (Integer.parseInt(quantity) - currentQuantity));
                cart.getProductQuantities().replace(product.getId(), Integer.parseInt(quantity));
            }
        }
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath());
    }
}