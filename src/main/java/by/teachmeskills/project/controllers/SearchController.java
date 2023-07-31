package by.teachmeskills.project.controllers;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import by.teachmeskills.project.exception.SQLExecutionException;
import by.teachmeskills.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final ProductService productService;

    @Autowired
    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView getSearchPage() throws SQLExecutionException {
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), productService.read());
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath(), model);
    }

    @PostMapping
    public ModelAndView submitSearch(@RequestParam(name = "searchField") String searchString) throws SQLExecutionException {
        ModelMap model = new ModelMap();
        List<Product> productList = productService.getSearchedProducts(searchString);
        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), productList);
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath(), model);
    }
}
