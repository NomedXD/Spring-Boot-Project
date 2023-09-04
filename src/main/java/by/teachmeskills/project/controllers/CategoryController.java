package by.teachmeskills.project.controllers;

import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final ProductService productService;

    @Autowired
    public CategoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{categoryId}")
    public ModelAndView getCategoryPage(@PathVariable(name = "categoryId") Integer categoryId) {
        return productService.getPaginatedProductsByCategoryId(categoryId, 1, EshopConstants.MIN_PAGE_SIZE);
    }

    @GetMapping("/{categoryId}/page/{page}")
    public ModelAndView changeCategoryPage(@PathVariable(name = "categoryId") Integer categoryId, @PathVariable(name = "page") Integer currentPage,
                                         @RequestParam(name = "size") Integer pageSize) throws EntityOperationException {
        return productService.getPaginatedProductsByCategoryId(categoryId, currentPage, pageSize);
    }

    @GetMapping("/{categoryId}/sized")
    public ModelAndView changeCategoryPageSize(@PathVariable(name = "categoryId") Integer categoryId, @RequestParam(name = "size") Integer pageSize) {
        return productService.getPaginatedProductsByCategoryId(categoryId, 1, pageSize);
    }

    @GetMapping("/export/{categoryId}")
    public void exportCategoryProducts(@PathVariable(name = "categoryId") Integer categoryId, HttpServletResponse response) throws CSVExportException {
        productService.exportCategoryProducts(categoryId, response);
    }

    @PostMapping("/import/{categoryId}")
    public ModelAndView importCategoryProducts(@RequestParam(name = "file") MultipartFile file, @PathVariable(name = "categoryId") Integer categoryId) throws CSVImportException {
        return productService.importCategoryProducts(file, categoryId);
    }
}
