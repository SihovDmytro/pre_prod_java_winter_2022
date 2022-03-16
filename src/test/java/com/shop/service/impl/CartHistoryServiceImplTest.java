package com.shop.service.impl;

import com.shop.dao.impl.CartHistoryDAOImpl;
import com.shop.entity.Furniture;
import com.shop.entity.Product;
import com.shop.service.CartHistoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

class CartHistoryServiceImplTest {
    CartHistoryService cartHistoryService;

    @Test
    public void shouldReturnLast5ItemsInCart() {
        cartHistoryService = new CartHistoryServiceImpl(new CartHistoryDAOImpl());
        Product product1 = new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140);
        Product product2 = new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198);
        cartHistoryService.add(product1, 1);
        cartHistoryService.add(product2, 2);
        cartHistoryService.add(product1, 1);

        List<Product> expected = Arrays.asList(product1, product2);
        List<Product> actual = cartHistoryService.getLastProducts(5);

        Assertions.assertEquals(expected, actual);
    }

}