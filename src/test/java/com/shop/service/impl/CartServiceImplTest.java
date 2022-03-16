package com.shop.service.impl;

import com.shop.dao.impl.CartDAOImpl;
import com.shop.entity.Furniture;
import com.shop.entity.Product;
import com.shop.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceImplTest {
    private CartService cartService;

    @Test
    public void shouldAddProductToCart() {
        cartService = new CartServiceImpl(new CartDAOImpl());
        Product product = new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140);

        cartService.add(product, 1);
        cartService.add(product, 2);
        HashMap<Product, Integer> itemsInCart = cartService.getCart();

        boolean check = itemsInCart.size() == 1 && itemsInCart.get(product) == 3;

        Assertions.assertTrue(check);
    }

    @Test
    public void shouldReturnCartPrice() {
        cartService = new CartServiceImpl(new CartDAOImpl());
        Product product1 = new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140);
        Product product2 = new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198);

        cartService.add(product1, 1);
        cartService.add(product2, 2);
        BigDecimal expected = new BigDecimal(2499 + 5500 * 2);
        BigDecimal actual = cartService.getTotalPrice();

        Assertions.assertEquals(expected, actual);
    }
}