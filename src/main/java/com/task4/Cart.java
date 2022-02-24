package com.task4;

import com.task1.subtask1.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private HashMap<Product, Integer> cart = new HashMap<>();
    private LinkedHashMap<Product, Integer> last5Products = new LinkedHashMap<>();

    public Cart() {
    }

    public void addToCart(Product product, int number) {
        if (!cart.containsKey(product))
            cart.put(product, number);
        else {
            cart.replace(product, cart.get(product) + number);
        }
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
