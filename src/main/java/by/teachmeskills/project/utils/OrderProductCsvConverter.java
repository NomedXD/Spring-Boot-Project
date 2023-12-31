package by.teachmeskills.project.utils;

import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.dto.OrderProductCsv;
import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.services.CategoryService;
import by.teachmeskills.project.services.DiscountCodeService;
import by.teachmeskills.project.services.ImageService;
import by.teachmeskills.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class OrderProductCsvConverter {
    private final UserService userService;
    private final ImageService imageService;
    private final CategoryService categoryService;
    private final DiscountCodeService discountCodeService;

    @Autowired
    public OrderProductCsvConverter(UserService userService,
                                    ImageService imageService,
                                    CategoryService categoryService,
                                    DiscountCodeService discountCodeService) {
        this.userService = userService;
        this.imageService = imageService;
        this.categoryService = categoryService;
        this.discountCodeService = discountCodeService;
    }

    public List<OrderProductCsv> convertInto(List<Order> orderList) {
        List<OrderProductCsv> orderProductCsvList = new ArrayList<>();
        orderList.forEach(order -> order.getProductList().forEach(product -> orderProductCsvList.add(OrderProductCsv.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productImageId(product.getImages().get(0).getId())
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
                .discountCodeId(order.getDiscountCode().getId())
                .address(order.getAddress())
                .customerNotes(order.getCustomerNotes()).build())));
        return orderProductCsvList;
    }

    public List<Order> convertFrom(List<OrderProductCsv> orderProductCsvList) {
        return map(orderProductCsvList);
    }

    private List<Order> map(List<OrderProductCsv> orderProductCsvList) {
        Map<Integer, Order> orderMap = new HashMap<>();
        orderProductCsvList.forEach(orderProductCsv -> {
            if (!orderMap.containsKey(orderProductCsv.getOrderId())) {
                Order order = Order.builder()
                        .id(0) // Здесь обязательно нужен 0, иначе не сохранится из-за вложенных объектов :(
                        .price(orderProductCsv.getTotalOrderPrice())
                        .date(orderProductCsv.getOrderDate())
                        .user(userService.getUserById(orderProductCsv.getUserId()).orElse(null))
                        .productList(new ArrayList<>())
                        .creditCardNumber(orderProductCsv.getCreditCardNumber())
                        .shippingType(orderProductCsv.getShippingType())
                        .shippingCost(orderProductCsv.getShippingCost())
                        .discountCode(discountCodeService.getDiscountCodeById(orderProductCsv.getDiscountCodeId()).orElse(null))
                        .address(orderProductCsv.getAddress())
                        .customerNotes(orderProductCsv.getCustomerNotes()).build();
                Product product = Product.builder()
                        .id(orderProductCsv.getProductId())
                        .name(orderProductCsv.getProductName())
                        .images(new ArrayList<>(Collections.singletonList(imageService.getImageById(orderProductCsv.getProductImageId()).orElse(null))))
                        .description(orderProductCsv.getProductDescription())
                        .category(categoryService.getCategoryById(orderProductCsv.getCategoryId()).orElse(null))
                        .price(orderProductCsv.getProductPrice()).build();
                order.getProductList().add(product);
                orderMap.put(orderProductCsv.getOrderId(), order);
            } else {
                orderMap.get(orderProductCsv.getOrderId()).getProductList().add(
                        Product.builder()
                        .id(orderProductCsv.getProductId())
                        .name(orderProductCsv.getProductName())
                                .images(new ArrayList<>(Collections.singletonList(imageService.getImageById(orderProductCsv.getProductImageId()).orElse(null))))
                        .description(orderProductCsv.getProductDescription())
                        .category(categoryService.getCategoryById(orderProductCsv.getCategoryId()).orElse(null))
                        .price(orderProductCsv.getProductPrice()).build());
            }
        });
        return new ArrayList<>(orderMap.values());
    }
}
