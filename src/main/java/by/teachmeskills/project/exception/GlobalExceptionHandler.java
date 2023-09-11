package by.teachmeskills.project.exception;

import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(NoHandlerFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return new ModelAndView(PagesPathEnum.ERROR_PAGE.getPath());
    }

    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNoSuchUserException(NoSuchUserException exception) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("loginErrorMessage", exception.getMessage());
        return new ModelAndView(PagesPathEnum.LOG_IN_PAGE.getPath(), modelMap);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleUserAlreadyExistException(UserAlreadyExistException exception) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("registrationErrorMessage", exception.getMessage());
        return new ModelAndView(PagesPathEnum.REGISTRATION_PAGE.getPath(), modelMap);
    }

    @ExceptionHandler(EntityOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleEntityOperationException(EntityOperationException exception) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("errorMessage", exception.getMessage());
        return new ModelAndView(PagesPathEnum.ERROR_PAGE.getPath(), modelMap);
    }

    @ExceptionHandler({CSVExportException.class, CSVImportException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleCSVExportImportException(CSVExportException exception) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(RequestParamsEnum.EXPORT_IMPORT_MESSAGE.getValue(), exception.getMessage());
        return new ModelAndView(exception.getReturnPath(), modelMap);
    }

    @ExceptionHandler(NoSuchProductException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNoSuchProductException(NoSuchProductException exception) {
        logger.error(String.format("Product was not found by id %d. Check database", exception.getProductId()));
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("errorMessage", exception.getMessage());
        return new ModelAndView(PagesPathEnum.ERROR_PAGE.getPath(), modelMap);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        if (exception.getCause() instanceof ConstraintViolationException constraintViolationException) {
            if (constraintViolationException.getConstraintName().equals("users.mail")) {
                return handleUserAlreadyExistException(new UserAlreadyExistException("User with such email already exist"));
            }
        }
        return handleEntityOperationException(new EntityOperationException("How you even get there?"));
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleSecurityException() {
        logger.error("User got access to authorized page, but Principal.class return null");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("errorMessage", "Server error. Check us later");
        return new ModelAndView(PagesPathEnum.ERROR_PAGE.getPath(), modelMap);
    }
}

