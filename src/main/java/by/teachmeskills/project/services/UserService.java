package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.SQLExecutionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    User getUserByCredentials(String mail, String password) throws SQLExecutionException;

    ModelAndView updateAccountData(User updatedUserFields, User user) throws SQLExecutionException;

    ModelAndView logIn(User user) throws SQLExecutionException;

    ModelAndView register(User user, BindingResult bindingResult, String repeatPassword) throws SQLExecutionException;

    ModelAndView checkIfLoggedInUser(User user) throws SQLExecutionException;
}
