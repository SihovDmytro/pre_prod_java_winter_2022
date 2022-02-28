package com.task4;

import com.task1.subtask1.Furniture;
import com.task1.subtask1.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

class CartTest {
    private Cart cart;

    @Test
    public void shouldAddProductToCart() {
        cart = new Cart();
        Product product = new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140);

        cart.addToCart(product, 1);
        cart.addToCart(product, 2);
        HashMap<Product, Integer> itemsInCart = cart.getCartHashMap();

        boolean check = itemsInCart.size() == 1 && itemsInCart.get(product) == 3;

        Assertions.assertTrue(check);
    }

    @Test
    public void shouldReturnCartPrice() {
        cart = new Cart();
        Product product1 = new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140);
        Product product2 = new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198);

        cart.addToCart(product1, 1);
        cart.addToCart(product2, 2);
        BigDecimal expected = new BigDecimal(2499 + 5500 * 2);
        BigDecimal actual = cart.getTotalPrice();

        Assertions.assertEquals(expected, actual);
    }

}