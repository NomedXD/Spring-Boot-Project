package by.teachmeskills.project.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    /*
     *  Annotated for the future admin account in shop
     */
    @NotNull(message = "Field is null validation error")
    @Size(min = 0, max = 100, message = "Out of validation bounds")
    private final Map<Integer, Product> products;
    @NotNull(message = "Field is null validation error")
    @PositiveOrZero(message = "Field must be positive or zero")
    private float totalPrice;

    public Cart() {
        this.products = new HashMap<>();
        this.totalPrice = 0;
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
        totalPrice += product.getPrice();
    }

    public void removeProduct(int productId) {
        Product product = products.get(productId);
        products.remove(productId);
        totalPrice -= product.getPrice();
    }

    public static Cart checkCart(Product product, Cart cart){
        if (cart != null) {
            cart.addProduct(product);
        } else {
            cart = new Cart();
            cart.addProduct(product);
        }
        return cart;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public int getTotalSize() {
        return products.size();
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void clear() {
        products.clear();
    }

}
