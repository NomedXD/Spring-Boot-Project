package by.teachmeskills.project.controllers;

import by.teachmeskills.project.domain.Cart;
import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import by.teachmeskills.project.exception.SQLExecutionException;
import by.teachmeskills.project.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({EshopConstants.SHOPPING_CART})
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView getShoppingCart() {
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath());
    }

    @GetMapping("/remove/{productid}")
    public ModelAndView deleteProductFromCart(@PathVariable(name = "productid") Integer productId,
                                              @SessionAttribute(EshopConstants.SHOPPING_CART) Cart cart) {
        ModelMap model = new ModelMap();
        cart.removeProduct(productId);
        model.addAttribute(RequestParamsEnum.SHOPPING_CART_PRODUCTS.getValue(), cart);
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath(), model);
    }

    @GetMapping("/add/{productid}")
    public ModelAndView addProductToCart(@PathVariable(name = "productid") Integer productId,
                                         @SessionAttribute(name = EshopConstants.SHOPPING_CART, required = false) Cart cart) throws SQLExecutionException {
        ModelMap model = new ModelMap();
        Product product = productService.getProductById(productId);
        model.addAttribute(EshopConstants.SHOPPING_CART, Cart.checkCart(product, cart));
        model.addAttribute(RequestParamsEnum.PRODUCT.getValue(), product);
        return new ModelAndView(PagesPathEnum.PRODUCT_PAGE.getPath(), model);
    }
}
