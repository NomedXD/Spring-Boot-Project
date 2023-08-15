package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Cart;
import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.EntityOperationException;
import org.springframework.web.servlet.ModelAndView;

public interface OrderService extends BaseService<Order>{
    ModelAndView applyOrder(Order order, Cart cart, User user) throws EntityOperationException;
}
