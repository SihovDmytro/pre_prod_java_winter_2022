package com.shop.service.impl;

import com.shop.dao.impl.CartDAOImpl;
import com.shop.dao.impl.OrderDAOImpl;
import com.shop.entity.Furniture;
import com.shop.entity.Product;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.util.DateUtil;
import com.shop.util.ShopProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class OrderServiceImplTest {
    OrderService orderService;
    CartService cartService;

    @Test
    public void shouldAddNewOrder() {
        cartService = new CartServiceImpl(new CartDAOImpl());
        orderService = new OrderServiceImpl(new OrderDAOImpl(cartService));
        Product product1 = new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140);
        Product product2 = new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198);
        cartService.add(product1, 1);
        cartService.add(product2, 2);
        Calendar orderDate = Calendar.getInstance();
        orderService.makeOrder(orderDate);

        int expected = 1;
        int actual = orderService.getOrders().size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnNearestOrderByDate() {
        cartService = new CartServiceImpl(new CartDAOImpl());
        orderService = new OrderServiceImpl(new OrderDAOImpl(cartService));
        ShopProperties.loadProperties();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Product firstProduct = new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140);
        cartService.add(firstProduct, 1);
        Calendar firstOrderDate = DateUtil.stringToCalendar("28.02.2022 09:51:56", format);
        orderService.makeOrder(firstOrderDate);
        Product secondProduct = new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198);
        cartService.add(secondProduct, 2);
        Calendar secondOrderDate = DateUtil.stringToCalendar("01.12.2022 09:51:56", format);
        orderService.makeOrder(secondOrderDate);

        HashMap<Product, Integer> expectedProducts = new HashMap<>();
        expectedProducts.put(secondProduct, 2);
        Map.Entry<Calendar, HashMap<Product, Integer>> expected = new AbstractMap.SimpleEntry<>(secondOrderDate, expectedProducts);
        Calendar enteredDate = DateUtil.stringToCalendar("19.08.2022 09:51:56", format);
        Map.Entry<Calendar, HashMap<Product, Integer>> actual = orderService.getOrderByDate(enteredDate);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnOrdersForSpecifiedPeriod() {
        cartService = new CartServiceImpl(new CartDAOImpl());
        orderService = new OrderServiceImpl(new OrderDAOImpl(cartService));
        ShopProperties.loadProperties();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Product firstProduct = new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140);
        cartService.add(firstProduct, 1);
        Calendar firstOrderDate = DateUtil.stringToCalendar("28.02.2022 09:51:56", format);
        orderService.makeOrder(firstOrderDate);
        Product secondProduct = new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198);
        cartService.add(secondProduct, 2);
        Calendar secondOrderDate = DateUtil.stringToCalendar("01.12.2022 09:51:56", format);
        orderService.makeOrder(secondOrderDate);

        Calendar startDate = DateUtil.stringToCalendar("19.12.2021 09:51:56", format);
        Calendar endDate = DateUtil.stringToCalendar("02.12.2022 09:51:56", format);

        Assertions.assertEquals(2, orderService.getOrdersForPeriod(startDate, endDate).size());
    }
}