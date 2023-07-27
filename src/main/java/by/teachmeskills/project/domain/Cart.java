package by.teachmeskills.project.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private final Map<Integer, Product> products;
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
