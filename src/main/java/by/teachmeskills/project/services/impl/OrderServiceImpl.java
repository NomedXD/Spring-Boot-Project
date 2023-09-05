package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Cart;
import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.repositories.OrderRepository;
import by.teachmeskills.project.services.OrderService;
import by.teachmeskills.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Order create(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public List<Order> read() {
        return orderRepository.findAll();
    }

    @Override
    public Order update(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getPaginatedOrders(Integer currentPage, Integer pageSize, Integer userId) {
        Pageable pageable = PageRequest.of((currentPage - 1), pageSize);
        return orderRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public Long getCountUserOrders(Integer userId) {
        return orderRepository.countAllByUserId(userId);
    }

    @Override
    public ModelAndView applyOrder(Order order, Cart cart, User user) {
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
