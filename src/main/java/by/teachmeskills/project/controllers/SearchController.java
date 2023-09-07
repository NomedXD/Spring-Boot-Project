package by.teachmeskills.project.controllers;

import by.teachmeskills.project.domain.Search;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/search")
@SessionAttributes({EshopConstants.SEARCH_ENTITY})
public class SearchController {
    private final ProductService productService;

    @Autowired
    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView getSearchPage(@SessionAttribute(name = EshopConstants.SEARCH_ENTITY, required = false) Search search,
                                      @RequestParam(name = "page") Integer currentPage,
                                      @RequestParam(name = "size") Integer pageSize) {
        if (Optional.ofNullable(currentPage).isPresent() && Optional.ofNullable(pageSize).isPresent()) {
            return productService.getSearchedPaginatedProducts(search, currentPage, pageSize);
        } else {
            return productService.getSearchedPaginatedProducts(search, 1, EshopConstants.MIN_PAGE_SIZE);
        }
    }
    /*
    @GetMapping("/page/{page}")
    public ModelAndView changeSearchPage() {
        return productService.getSearchedPaginatedProducts(search, currentPage, pageSize);
    }

    @GetMapping("/sized")
    public ModelAndView changeSearchPageSize(@SessionAttribute(name = EshopConstants.SEARCH_ENTITY, required = false) Search search,
                                             @RequestParam(name = "size") Integer pageSize) {
        return productService.getSearchedPaginatedProducts(search, 1, pageSize);
    }

     */

    @PostMapping
    public ModelAndView submitSearch(@ModelAttribute(EshopConstants.SEARCH_ENTITY) Search search,
                                     @RequestParam(name = "size") Integer pageSize) {
        return productService.getSearchedPaginatedProducts(search, 1, pageSize);
    }

    @ModelAttribute(EshopConstants.SEARCH_ENTITY)
    public Search setUpSearchEntity() {
        return new Search();
    }
}
