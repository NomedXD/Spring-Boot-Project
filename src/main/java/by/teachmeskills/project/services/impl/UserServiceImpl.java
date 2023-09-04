package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.exception.NoSuchUserException;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.UserRepository;
import by.teachmeskills.project.services.CategoryService;
import by.teachmeskills.project.services.UserService;
import by.teachmeskills.project.domain.OrderProductCsv;
import by.teachmeskills.project.utils.OrderProductCsvConverter;
import by.teachmeskills.project.validator.ValidatorUtils;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final OrderProductCsvConverter orderProductCsvConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CategoryService categoryService, @Lazy OrderProductCsvConverter orderProductCsvConverter) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.orderProductCsvConverter = orderProductCsvConverter;
    }

    @Override
    public User create(User entity) throws EntityOperationException {
        return userRepository.save(entity);
    }

    @Override
    public List<User> read() throws EntityOperationException {
        return userRepository.findAll();
    }

    @Override
    public User update(User entity) throws EntityOperationException {
        return userRepository.save(entity);
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Integer id) throws EntityOperationException {
        return userRepository.findById(id);
    }

    @Override
    public ModelAndView updateAccountData(User updatedUserFields, User user) throws EntityOperationException {
        Map<String, String> params = new HashMap<>();
        params.put(RequestParamsEnum.MOBILE.getValue(), updatedUserFields.getMobile());
        params.put(RequestParamsEnum.STREET.getValue(), updatedUserFields.getStreet());
        params.put(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue(), updatedUserFields.getAccommodationNumber());
        params.put(RequestParamsEnum.FLAT_NUMBER.getValue(), updatedUserFields.getFlatNumber());
        setInputs(params, user);
        updatedUserFields = User.builder().id(user.getId()).mail(user.getMail()).password(user.getPassword()).
                name(user.getName()).surname(user.getSurname()).date(user.getDate()).currentBalance(user.getCurrentBalance()).
                mobile(params.get(RequestParamsEnum.MOBILE.getValue())).street(params.get(RequestParamsEnum.STREET.getValue())).
                accommodationNumber(params.get(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue())).
                flatNumber(params.get(RequestParamsEnum.FLAT_NUMBER.getValue())).build();
        ModelMap model = new ModelMap();
        updatedUserFields.setOrders(userRepository.findOrdersByUserId(user.getId()));
        user = update(updatedUserFields);
        model.addAttribute(EshopConstants.USER, user);
        return new ModelAndView(PagesPathEnum.ACCOUNT_PAGE.getPath(), model);
    }

    private void setInputs(Map<String, String> params, User user) {
        for (var entry : params.entrySet()) {
            if (entry.getValue().isEmpty()) {
                switch (entry.getKey()) {
                    case "mobile" -> entry.setValue(user.getMobile());
                    case "street" -> entry.setValue(user.getStreet());
                    case "accommodationNumber" -> entry.setValue(user.getAccommodationNumber());
                    case "flatNumber" -> entry.setValue(user.getFlatNumber());
                }
            }
        }
    }

    @Override
    public ModelAndView logIn(User user) throws EntityOperationException {
        Optional<User> loggedUser = userRepository.findUserByMailAndPassword(user.getMail(), user.getPassword());
        if (loggedUser.isPresent()) {
            user = loggedUser.get();
            ModelAndView modelAndView = categoryService.getPaginatedCategories(1, EshopConstants.MIN_PAGE_SIZE);
            modelAndView.getModelMap().addAttribute(EshopConstants.USER, user);
            return modelAndView;
        } else {
            throw new NoSuchUserException("Wrong email or password. Try again");
        }
    }

    @Override
    public ModelAndView register(User user, BindingResult bindingResult, String repeatPassword) throws EntityOperationException {
        if (!bindingResult.hasErrors() && ValidatorUtils.validatePasswordMatching(user.getPassword(), repeatPassword)) {
            ModelMap model = new ModelMap();
            user = userRepository.save(User.builder().mail(user.getMail()).password(user.getPassword()).name(user.getName()).
                    surname(user.getSurname()).date(user.getDate()).currentBalance(0f).orders(new ArrayList<>()).build());
            model.addAttribute(EshopConstants.USER, user);
            model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categoryService.read());
            return new ModelAndView(PagesPathEnum.SHOP_PAGE.getPath(), model);
        }
        return new ModelAndView(PagesPathEnum.REGISTRATION_PAGE.getPath());
    }

    @Override
    public ModelAndView checkIfLoggedInUser(User user) throws EntityOperationException {
        ModelMap model = new ModelMap();
        if (user != null) {
            return categoryService.getPaginatedCategories(1, EshopConstants.MIN_PAGE_SIZE);
        } else {
            return new ModelAndView(PagesPathEnum.LOG_IN_PAGE.getPath(), model);
        }
    }

    @Override
    public void exportUserOrders(User user, HttpServletResponse response) throws CSVExportException {
        writeCsv(user, response);
    }

    private void writeCsv(User user, HttpServletResponse response) throws CSVExportException {
        List<Order> orderList = user.getOrders();
        List<OrderProductCsv> orderProductCsvList = orderProductCsvConverter.convertInto(orderList);
        try (Writer ordersProductsWriter = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv<OrderProductCsv> ordersProductsSbc = new StatefulBeanToCsvBuilder<OrderProductCsv>(ordersProductsWriter)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + "user_" + user.getId() + "_orders_products.csv");
            ordersProductsSbc.write(orderProductCsvList);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new CSVExportException(EshopConstants.errorOrdersExportMessage, PagesPathEnum.ACCOUNT_PAGE.getPath());
        }
    }

    @Override
    public ModelAndView importUserOrders(MultipartFile file, User user) throws CSVImportException {
        List<Order> orderList = parseCsv(file);
        User finalUser = user;
        orderList.forEach(order -> finalUser.getOrders().add(order));
        user = userRepository.save(finalUser);
        ModelMap model = new ModelMap();
        model.addAttribute(EshopConstants.USER, user);
        model.addAttribute(RequestParamsEnum.EXPORT_IMPORT_MESSAGE.getValue(), EshopConstants.successfulImportMessage);
        return new ModelAndView(PagesPathEnum.ACCOUNT_PAGE.getPath(), model);
    }

    private List<Order> parseCsv(MultipartFile file) throws CSVImportException {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader ordersProdcutsReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<OrderProductCsv> ordersProductsCtb = new CsvToBeanBuilder<OrderProductCsv>(ordersProdcutsReader)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withType(OrderProductCsv.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                List<OrderProductCsv> orderProductCsvList = ordersProductsCtb.parse();
                return orderProductCsvConverter.convertFrom(orderProductCsvList);
            } catch (IOException e) {
                throw new CSVImportException(EshopConstants.errorOrdersImportMessage, PagesPathEnum.ACCOUNT_PAGE.getPath());
            }
        } else {
            throw new CSVImportException(EshopConstants.errorFileNullMessage, PagesPathEnum.ACCOUNT_PAGE.getPath());
        }
    }
}
