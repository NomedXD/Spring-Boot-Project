package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.User;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    User getUserByCredentials(String mail, String password);

    ModelAndView updateAccountData(User updatedUserFields, User user);

    ModelAndView logIn(User user);

    ModelAndView register(User user, String repeatPassword);

    ModelAndView checkIfLoggedInUser(User user);
}
