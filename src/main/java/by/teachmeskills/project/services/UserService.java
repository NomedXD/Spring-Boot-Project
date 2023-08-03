package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.EntityOperationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    User getUserByCredentials(String mail, String password) throws EntityOperationException;

    ModelAndView updateAccountData(User updatedUserFields, User user) throws EntityOperationException;

    ModelAndView logIn(User user) throws EntityOperationException;

    ModelAndView register(User user, BindingResult bindingResult, String repeatPassword) throws EntityOperationException;

    ModelAndView checkIfLoggedInUser(User user) throws EntityOperationException;
}
