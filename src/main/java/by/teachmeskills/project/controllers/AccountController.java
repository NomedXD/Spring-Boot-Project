package by.teachmeskills.project.controllers;

import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.services.UserService;
import by.teachmeskills.project.utils.SecurityContextUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getAccountPage(@RequestParam(name = "page", required = false) Integer currentPage,
                                       @RequestParam(name = "size", required = false) Integer pageSize) {
        User user = SecurityContextUtils.getUser().orElseThrow(SecurityException::new);
        return userService.getAccount(user.getId(), currentPage, pageSize);
    }

    @PostMapping("/update")
    public ModelAndView updateAccountData(@ModelAttribute("updatedUserFields") User updatedUserFields,
                                          @RequestParam(name = "page") Integer currentPage,
                                          @RequestParam(name = "size") Integer pageSize) {
        User user = SecurityContextUtils.getUser().orElseThrow(SecurityException::new);
        return userService.updateAccountData(updatedUserFields, user, currentPage, pageSize);
    }

    @GetMapping("/export")
    public void exportUserOrders(HttpServletResponse response) throws CSVExportException {
        User user = SecurityContextUtils.getUser().orElseThrow(SecurityException::new);
        userService.exportUserOrders(user, response);
    }

    @PostMapping("/import")
    public ModelAndView importUserOrders(@RequestParam(name = "file") MultipartFile file) throws CSVImportException {
        User user = SecurityContextUtils.getUser().orElseThrow(SecurityException::new);
        return userService.importUserOrders(file, user);
    }

    @ModelAttribute("updatedUserFields")
    public User setUpUpdatedUser() {
        return new User();
    }
}
