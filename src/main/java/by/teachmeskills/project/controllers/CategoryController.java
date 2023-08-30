package by.teachmeskills.project.controllers;

import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @GetMapping("/{categoryid}")
    public ModelAndView getCategoryPage(@PathVariable(name = "categoryid") Integer categoryId) throws EntityOperationException {
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), productService.getCategoryProducts(categoryId));
        return new ModelAndView(PagesPathEnum.CATEGORY_PAGE.getPath(), model);
    }

    @GetMapping("/export/{categoryId}")
    public ModelAndView exportCategoryProducts(@PathVariable(name = "categoryId") Integer categoryId) throws CSVExportException {
        return productService.exportCategoryProducts(categoryId);
    }

    @PostMapping("/import/{categoryId}")
    public ModelAndView importCategoryProducts(@RequestParam(name = "file") MultipartFile file, @PathVariable(name = "categoryId") Integer categoryId) throws CSVImportException {
        return productService.importCategoryProducts(file, categoryId);
    }
}
