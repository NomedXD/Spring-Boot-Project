package by.teachmeskills.project.controllers;

import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.services.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/catalog")
public class HomeController {
    private final CategoryService categoryService;

    @Autowired
    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ModelAndView getHomePage(@RequestParam(name = "page", required = false) Integer currentPage,
                                    @RequestParam(name = "size", required = false) Integer pageSize) {
        return categoryService.getPaginatedCategories(currentPage, pageSize);
    }

    @GetMapping("/export")
    public void exportCategories(HttpServletResponse response) throws CSVExportException {
        categoryService.exportCategories(response);
    }

    @PostMapping("/import")
    public ModelAndView importUserOrders(@RequestParam(name = "file") MultipartFile file) throws CSVImportException {
        return categoryService.importCategories(file);
    }
}
