package by.teachmeskills.project.controllers;

import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.services.CategoryService;
import by.teachmeskills.project.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/catalog")
public class HomeController {
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public HomeController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ModelAndView getHomePage(@SessionAttribute(name = EshopConstants.USER, required = false) User user) throws EntityOperationException {
        return userService.checkIfLoggedInUser(user);
    }

    @GetMapping("/page/{page}")
    public ModelAndView changeHomePage(@PathVariable(name = "page") Integer currentPage,
                                       @RequestParam(name = "size") Integer pageSize) throws EntityOperationException {
        return categoryService.getPaginatedCategories(currentPage, pageSize);
    }

    @GetMapping("/sized")
    public ModelAndView changeCategoryPageSize(@RequestParam(name = "size") Integer pageSize) {
        return categoryService.getPaginatedCategories(1, pageSize);
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
