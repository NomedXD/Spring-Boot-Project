package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Cart;
import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.OrderRepository;
import by.teachmeskills.project.services.OrderService;
import by.teachmeskills.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    public Order create(Order entity) throws EntityOperationException {
        return orderRepository.save(entity);
    }

    @Override
    public List<Order> read() throws EntityOperationException {
        return orderRepository.findAll();
    }

    @Override
    public Order update(Order entity) throws EntityOperationException {
        return orderRepository.save(entity);
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        orderRepository.deleteById(id);
    }

    @Override
    public ModelAndView applyOrder(Order order, Cart cart, User user) throws EntityOperationException {
        preBuildOrder(order, cart, user);
        user.getOrders().add(create(order));
        user = userService.update(user);
        cart.clear();
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath());
    }

    private void preBuildOrder(Order order, Cart cart, User user) {
        order.setDate(LocalDate.now());
        order.setPrice(cart.getTotalPrice());
        String ccNumber = order.getCreditCardNumber();
        order.setCreditCardNumber(ccNumber.substring(0, 5).concat(" **** **** ").concat(ccNumber.substring(12, 16)));
        order.setUser(user);
        order.setProductList(cart.getProducts());
    }
}
