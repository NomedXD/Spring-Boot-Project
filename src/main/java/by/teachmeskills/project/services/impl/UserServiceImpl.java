package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.principal.UserPrincipal;
import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.domain.Role;
import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import by.teachmeskills.project.enums.UserRoleEnum;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.repositories.UserRepository;
import by.teachmeskills.project.services.OrderService;
import by.teachmeskills.project.services.UserService;
import by.teachmeskills.project.dto.OrderProductCsv;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final OrderProductCsvConverter orderProductCsvConverter;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           @Lazy OrderProductCsvConverter orderProductCsvConverter, @Lazy OrderService orderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderProductCsvConverter = orderProductCsvConverter;
        this.orderService = orderService;
    }

    @Override
    public User create(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return userRepository.save(entity);
    }

    @Override
    public List<User> read() {
        return userRepository.findAll();
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByMail(String userMail) {
        return userRepository.findUserByMail(userMail);
    }

    @Override
    public ModelAndView getAccount(Integer userId, Integer currentPage, Integer pageSize) {
        if (Optional.ofNullable(currentPage).isEmpty() || Optional.ofNullable(pageSize).isEmpty()) {
            currentPage = 1;
            pageSize = EshopConstants.MIN_PAGE_SIZE;
        }
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.CURRENT_PAGE.getValue(), currentPage);
        model.addAttribute(RequestParamsEnum.PAGE_SIZE.getValue(), pageSize);
        model.addAttribute(RequestParamsEnum.TOTAL_PAGINATED_VISIBLE_PAGES.getValue(), EshopConstants.TOTAL_PAGINATED_VISIBLE_PAGES);
        model.addAttribute(RequestParamsEnum.LAST_PAGE_NUMBER.getValue(), Math.ceil(orderService.getCountUserOrders(userId) / pageSize.doubleValue()));
        model.addAttribute(RequestParamsEnum.ORDERS.getValue(), orderService.getPaginatedOrders(currentPage, pageSize, userId));

        return new ModelAndView(PagesPathEnum.ACCOUNT_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView updateAccountData(User updatedUserFields, User user, Integer currentPage, Integer pageSize) {
        Map<String, String> params = new HashMap<>();
        params.put(RequestParamsEnum.MOBILE.getValue(), updatedUserFields.getMobile());
        params.put(RequestParamsEnum.STREET.getValue(), updatedUserFields.getStreet());
        params.put(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue(), updatedUserFields.getAccommodationNumber());
        params.put(RequestParamsEnum.FLAT_NUMBER.getValue(), updatedUserFields.getFlatNumber());
        setInputs(params, user);
        updatedUserFields = User.builder().id(user.getId()).mail(user.getMail()).password(user.getPassword()).
                name(user.getName()).surname(user.getSurname()).date(user.getDate()).
                mobile(params.get(RequestParamsEnum.MOBILE.getValue())).street(params.get(RequestParamsEnum.STREET.getValue())).
                accommodationNumber(params.get(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue())).
                flatNumber(params.get(RequestParamsEnum.FLAT_NUMBER.getValue())).roles(user.getRoles()).build();
        ModelMap model = new ModelMap();
        updatedUserFields.setOrders(userRepository.findOrdersByUserId(user.getId()));
        ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setUser(update(updatedUserFields));
        model.addAttribute(RequestParamsEnum.CURRENT_PAGE.getValue(), currentPage);
        model.addAttribute(RequestParamsEnum.PAGE_SIZE.getValue(), pageSize);
        model.addAttribute(RequestParamsEnum.TOTAL_PAGINATED_VISIBLE_PAGES.getValue(), EshopConstants.TOTAL_PAGINATED_VISIBLE_PAGES);
        model.addAttribute(RequestParamsEnum.LAST_PAGE_NUMBER.getValue(), Math.ceil(orderService.getCountUserOrders(user.getId()) / pageSize.doubleValue()));
        model.addAttribute(EshopConstants.ORDERS, orderService.getPaginatedOrders(currentPage, pageSize, user.getId()));
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
    public ModelAndView logIn(String error) {
        ModelAndView modelAndView = new ModelAndView(PagesPathEnum.LOG_IN_PAGE.getPath());
        if (error != null) {
            modelAndView.addObject("loginErrorMessage", "Wrong email or password. Try again");
        }
        return modelAndView;
    }

    @Override
    public ModelAndView register(User user, BindingResult bindingResult, String repeatPassword) {
        if (!bindingResult.hasErrors() && ValidatorUtils.validatePasswordMatching(user.getPassword(), repeatPassword)) {
            userRepository.save(User.builder().mail(user.getMail()).password(passwordEncoder.encode(user.getPassword())).name(user.getName()).
                    surname(user.getSurname()).date(user.getDate()).orders(new ArrayList<>()).roles(List.of(Role.builder().id(2).name(UserRoleEnum.USER.name()).build())).build());
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("loginErrorMessage", "Now you can log in");
            return new ModelAndView("redirect:/login", modelMap);
        }
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("registrationErrorMessage", "Wrong credentials matching");
        return new ModelAndView(PagesPathEnum.REGISTRATION_PAGE.getPath(), modelMap);
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
