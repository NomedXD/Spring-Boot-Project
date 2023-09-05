package by.teachmeskills.project.controllers;

import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({EshopConstants.USER})
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getAccountPage(@SessionAttribute(value = EshopConstants.USER, required = false) User user) {
        return userService.getAccount(user.getId(), 1, EshopConstants.MIN_PAGE_SIZE);
    }

    @PostMapping("/update/page/{page}")
    public ModelAndView updateAccountData(@ModelAttribute("updatedUserFields") User updatedUserFields,
                                          @SessionAttribute(EshopConstants.USER) User user,
                                          @PathVariable(name = "page") Integer currentPage,
                                          @RequestParam(name = "size") Integer pageSize) {
        return userService.updateAccountData(updatedUserFields, user, currentPage, pageSize);
    }

    @GetMapping("/page/{page}")
    public ModelAndView changeAccountPage(@SessionAttribute(EshopConstants.USER) User user,
                                          @PathVariable(name = "page") Integer currentPage,
                                          @RequestParam(name = "size") Integer pageSize) {
        return userService.getAccount(user.getId(), currentPage, pageSize);
    }

    @GetMapping("/sized")
    public ModelAndView changeAccountPageSize(@SessionAttribute(EshopConstants.USER) User user,
                                              @RequestParam(name = "size") Integer pageSize) {
        return userService.getAccount(user.getId(), 1, pageSize);
    }


    @GetMapping("/export")
    public void exportUserOrders(@SessionAttribute(EshopConstants.USER) User user, HttpServletResponse response) throws CSVExportException {
        userService.exportUserOrders(user, response);
    }

    @PostMapping("/import")
    public ModelAndView importUserOrders(@RequestParam(name = "file") MultipartFile file, @SessionAttribute(EshopConstants.USER) User user) throws CSVImportException {
        return userService.importUserOrders(file, user);
    }

    @ModelAttribute("updatedUserFields")
    public User setUpUpdatedUser() {
        return new User();
    }
}
