package com.task4;

import com.task1.subtask1.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private HashMap<Product, Integer> cart = new HashMap<>();
    private LinkedHashMap<Product, Integer> last5Products = new LinkedHashMap<>();
    private static final Logger LOG = LogManager.getLogger(Cart.class);

    public Cart() {
    }

    public HashMap<Product, Integer> getCartHashMap() {
        return cart;
    }

    public void addToCart(Product product, int number) {
        if(number<1) return;
        if (!cart.containsKey(product)) {
            cart.put(product, number);
        }
        else {
            cart.replace(product, cart.get(product) + number);
        }
        LOG.debug("Add product to the cart: "+product.getName());
    }

    public void removeFromCart(Product product, int number) {
        Integer currentNumber = cart.get(product);
        if (currentNumber != null && currentNumber - number > 0) {
            cart.replace(product, currentNumber - number);
        } else {
            cart.remove(product);
        }
    }

    public BigDecimal makeOrder()
    {
        BigDecimal totalPrice = new BigDecimal(0);
        for(Map.Entry<Product,Integer> entry : cart.entrySet())
        {
            BigDecimal price = entry.getKey().getPrice();
            BigDecimal number = new BigDecimal(entry.getValue());
            totalPrice = totalPrice.add(price.multiply(number));
        }
        cart.clear();
        return totalPrice;
    }


}
