package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Cart;
import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.domain.User;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface OrderService extends BaseService<Order>{
    List<Order> getPaginatedOrders(Integer currentPage, Integer pageSize, Integer userId);
    Long getCountUserOrders(Integer userId);
    ModelAndView applyOrder(Order order, Cart cart, User user);
}
