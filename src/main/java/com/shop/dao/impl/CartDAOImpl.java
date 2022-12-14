package com.shop.dao.impl;

import com.shop.dao.CartDAO;
import com.shop.entity.Cart;
import com.shop.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;

public class CartDAOImpl implements CartDAO {
    private Cart cart;
    private static final Logger LOG = LogManager.getLogger(CartDAOImpl.class);

    public CartDAOImpl(Cart cart) {
        this.cart = cart;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public boolean add(Product product, int number) {
        if (number < 1) return false;
        if (!cart.containsKey(product)) {
            cart.put(product, number);
        } else {
            cart.replace(product, cart.get(product) + number);
        }
        LOG.debug("Add product to the cart: " + product.getName());
        return true;
    }

    @Override
    public void clearCart() {
        cart = new Cart();
        LOG.trace("Cart is empty");
    }

    @Override
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
