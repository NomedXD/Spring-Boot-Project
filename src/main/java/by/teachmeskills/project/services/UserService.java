package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public interface UserService extends BaseService<User> {
    Optional<User> getUserById(Integer id);

    Optional<User> getUserByMail(String userMail);

    ModelAndView getAccount(Integer userId, Integer currentPage, Integer pageSize);

    ModelAndView updateAccountData(User updatedUserFields, User user, Integer currentPage, Integer pageSize);

    ModelAndView logIn(String error);

    ModelAndView register(User user, BindingResult bindingResult, String repeatPassword);

    void exportUserOrders(User user, HttpServletResponse response) throws CSVExportException;

    ModelAndView importUserOrders(MultipartFile file, User user) throws CSVImportException;
}
