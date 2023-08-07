package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import by.teachmeskills.project.exception.NoSuchUserException;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.exception.UserAlreadyExistException;
import by.teachmeskills.project.repositories.UserRepository;
import by.teachmeskills.project.services.CategoryService;
import by.teachmeskills.project.services.UserService;
import by.teachmeskills.project.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CategoryService categoryService) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    @Override
    public User create(User entity) throws EntityOperationException {
        return userRepository.create(entity);
    }

    @Override
    public List<User> read() throws EntityOperationException {
        return userRepository.read();
    }

    @Override
    public User update(User entity) throws EntityOperationException {
        return userRepository.update(entity);
    }

    @Override
    public void delete(int id) throws EntityOperationException {
        userRepository.delete(id);
    }

    @Override
    public User getUserByCredentials(String mail, String password) throws EntityOperationException {
        return userRepository.getUserByCredentials(mail, password);
    }

    @Override
    public ModelAndView updateAccountData(User updatedUserFields, User user) throws EntityOperationException {
        Map<String, String> params = new HashMap<>();
        params.put(RequestParamsEnum.MOBILE.getValue(), updatedUserFields.getMobile());
        params.put(RequestParamsEnum.STREET.getValue(), updatedUserFields.getStreet());
        params.put(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue(), updatedUserFields.getAccommodationNumber());
        params.put(RequestParamsEnum.FLAT_NUMBER.getValue(), updatedUserFields.getFlatNumber());
        setInputs(params, user);
        updatedUserFields = new User(user.getId(), user.getMail(), user.getPassword(), user.getName(), user.getSurname(),
                user.getDate(), user.getCurrentBalance(), params.get(RequestParamsEnum.MOBILE.getValue()), params.get(RequestParamsEnum.STREET.getValue()),
                params.get(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue()), params.get(RequestParamsEnum.FLAT_NUMBER.getValue()));
        ModelMap model = new ModelMap();
        updatedUserFields.setOrders(user.getOrders());
        user = update(updatedUserFields);
        model.addAttribute(EshopConstants.USER, user);
        return new ModelAndView(PagesPathEnum.ACCOUNT_PAGE.getPath(), model);
    }

    private void setInputs(Map<String, String> params, User user) {
        for (var entry : params.entrySet()) {
            if (entry.getValue().equals("")) {
                switch (entry.getKey()) {
                    case "mobile" -> {
                        entry.setValue(user.getMobile());
                    }
                    case "street" -> {
                        entry.setValue(user.getStreet());
                    }
                    case "accommodationNumber" -> {
                        entry.setValue(user.getAccommodationNumber());
                    }
                    case "flatNumber" -> {
                        entry.setValue(user.getFlatNumber());
                    }
                }
            }
        }
    }

    @Override
    public ModelAndView logIn(User user) throws EntityOperationException {
        ModelMap model = new ModelMap();
        User loggedUser = getUserByCredentials(user.getMail(), user.getPassword());
        if (loggedUser != null) {
            model.addAttribute(EshopConstants.USER, loggedUser);
            model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categoryService.read());
            return new ModelAndView(PagesPathEnum.SHOP_PAGE.getPath(), model);
        } else {
            throw new NoSuchUserException("Wrong email or password. Try again");
        }
    }

    @Override
    public ModelAndView register(User user, BindingResult bindingResult, String repeatPassword) throws EntityOperationException {
        if (!bindingResult.hasErrors() && ValidatorUtils.validatePasswordMatching(user.getPassword(), repeatPassword)) {
            ModelMap model = new ModelMap();
            User loggedUser = create(new User(user.getMail(), user.getPassword(), user.getName(), user.getSurname(), user.getDate(), 0));
            if (loggedUser != null) {
                model.addAttribute(EshopConstants.USER, loggedUser);
                model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categoryService.read());
                return new ModelAndView(PagesPathEnum.SHOP_PAGE.getPath(), model);
            } else {
                throw new UserAlreadyExistException("User with such email already exist");
            }
        }
        return new ModelAndView(PagesPathEnum.REGISTRATION_PAGE.getPath());
    }

    @Override
    public ModelAndView checkIfLoggedInUser(User user) throws EntityOperationException {
        ModelMap model = new ModelMap();
        if (user != null) {
            List<Category> categoriesList = categoryService.read();
            model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categoriesList);
            return new ModelAndView(PagesPathEnum.SHOP_PAGE.getPath(), model);
        } else {
            return new ModelAndView(PagesPathEnum.LOG_IN_PAGE.getPath(), model);
        }
    }
}
