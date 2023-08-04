package by.teachmeskills.project.controllers;

import by.teachmeskills.project.domain.SearchEntity;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView getSearchPage() throws EntityOperationException {
        return productService.getSearchedProducts(null, 1);
    }

    @GetMapping("/{page}")
    /* Заметка сумасшедшего:
    required = false В SessionAttribute здесь не поможет, так как при любом первом обращении к контроллеру searchEntity добавится в модель и сессию.
    А все из-за того, что метод setUpSearchEntity вызывается всегда раньше остальных методов. AAAAAAAAAAAA WATAFACK
     */
    public ModelAndView changeSearchPage(@SessionAttribute(name = EshopConstants.SEARCH_ENTITY) SearchEntity searchEntity, @PathVariable(name = "page") Integer currentPage) throws EntityOperationException {
        return productService.getSearchedProducts(searchEntity, currentPage);
    }

    @PostMapping
    public ModelAndView submitSearch(@ModelAttribute(EshopConstants.SEARCH_ENTITY) SearchEntity searchEntity) throws EntityOperationException {
        return productService.getSearchedProducts(searchEntity, 1);
    }

    @ModelAttribute(EshopConstants.SEARCH_ENTITY)
    public SearchEntity setUpSearchEntity() {
        return new SearchEntity();
    }
}
