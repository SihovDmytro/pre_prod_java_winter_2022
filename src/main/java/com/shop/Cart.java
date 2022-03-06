package com.shop;

import com.shop.dao.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private HashMap<Product, Integer> cart = new HashMap<>();
    private LinkedHashMap<Product, Integer> cartHistory = new LinkedHashMap<>();
    private static final Logger LOG = LogManager.getLogger(Cart.class);

    public Cart() {
    }


    public HashMap<Product, Integer> getCartHistory() {
        return cartHistory;
    }

    public HashMap<Product, Integer> getCartHashMap() {
        return cart;
    }

    public boolean addToCart(Product product, int number) {
        if (number < 1) return false;
        if (!cart.containsKey(product)) {
            cart.put(product, number);
        } else {
            cart.replace(product, cart.get(product) + number);
        }
        cartHistory.put(product, number);
        LOG.debug("Add product to the cart: " + product.getName());
        return true;
    }

    public void clearCart() {
        cart = new HashMap<>();
        LOG.trace("Cart is empty");
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            BigDecimal price = entry.getKey().getPrice();
            BigDecimal number = new BigDecimal(entry.getValue());
            totalPrice = totalPrice.add(price.multiply(number));
        }
        LOG.debug("Total price of cart: " + totalPrice);
        return totalPrice;
    }

}
