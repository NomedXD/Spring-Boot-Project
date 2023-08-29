package by.teachmeskills.project.utils;

import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.domain.OrderProductCsv;
import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.services.CategoryService;
import by.teachmeskills.project.services.ImageService;
import by.teachmeskills.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class OrderProductCsvConverter {
    private final UserService userService;
    private final ImageService imageService;
    private final CategoryService categoryService;

    @Autowired
    public OrderProductCsvConverter(UserService userService, ImageService imageService, CategoryService categoryService) {
        this.userService = userService;
        this.imageService = imageService;
        this.categoryService = categoryService;
    }

    public List<OrderProductCsv> convertInto(List<Order> orderList) {
        List<OrderProductCsv> orderProductCsvList = new ArrayList<>();
        orderList.forEach(order -> order.getProductList().forEach(product -> orderProductCsvList.add(OrderProductCsv.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productImageId(product.getImage().getId())
                .productDescription(product.getDescription())
                .categoryId(product.getCategory().getId())
                .productPrice(product.getPrice())
                .orderId(order.getId())
                .totalOrderPrice(order.getPrice())
                .orderDate(order.getDate())
                .userId(order.getUser().getId())
                .creditCardNumber(order.getCreditCardNumber())
                .shippingType(order.getShippingType())
                .shippingCost(order.getShippingCost())
                .code(order.getCode())
                .address(order.getAddress())
                .customerNotes(order.getCustomerNotes()).build())));
        return orderProductCsvList;
    }

    public List<Order> convertFrom(List<OrderProductCsv> orderProductCsvList) {
        return createOrderDtoList(orderProductCsvList);
    }

    private List<Order> createOrderDtoList(List<OrderProductCsv> orderProductCsvList) {
        Map<Integer, Order> orderMap = new HashMap<>();
        orderProductCsvList.forEach(orderProductCsv -> {
            if (!orderMap.containsKey(orderProductCsv.getOrderId())) {
                Order order = Order.builder()
                        .id(orderProductCsv.getOrderId())
                        .price(orderProductCsv.getTotalOrderPrice())
                        .date(orderProductCsv.getOrderDate())
                        .user(userService.getUserById(orderProductCsv.getUserId()))
                        .productList(new ArrayList<>())
                        .creditCardNumber(orderProductCsv.getCreditCardNumber())
                        .shippingType(orderProductCsv.getShippingType())
                        .shippingCost(orderProductCsv.getShippingCost())
                        .code(orderProductCsv.getCode())
                        .address(orderProductCsv.getAddress())
                        .customerNotes(orderProductCsv.getCustomerNotes()).build();
                Product product = Product.builder()
                        .id(orderProductCsv.getProductId())
                        .name(orderProductCsv.getProductName())
                        .image(imageService.getImageById(orderProductCsv.getProductImageId()))
                        .description(orderProductCsv.getProductDescription())
                        .category(categoryService.getCategoryById(orderProductCsv.getCategoryId()))
                        .price(orderProductCsv.getProductPrice()).build();
                order.getProductList().add(product);
                orderMap.put(orderProductCsv.getOrderId(), order);
            } else {
                orderMap.get(orderProductCsv.getOrderId()).getProductList().add(
                        Product.builder()
                        .id(orderProductCsv.getProductId())
                        .name(orderProductCsv.getProductName())
                        .image(imageService.getImageById(orderProductCsv.getProductImageId()))
                        .description(orderProductCsv.getProductDescription())
                        .category(categoryService.getCategoryById(orderProductCsv.getCategoryId()))
                        .price(orderProductCsv.getProductPrice()).build());
            }
        });
        return new ArrayList<>(orderMap.values());
    }
}
