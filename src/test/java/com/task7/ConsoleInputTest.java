package com.task7;

import com.shop.Runner;
import com.shop.dao.ConsoleInput;
import com.shop.dao.RandomInput;
import com.shop.dao.ShopDAO;
import com.shop.dao.entity.CannedFood;
import com.shop.dao.entity.Furniture;
import com.shop.dao.entity.Product;
import com.shop.util.Localization;
import com.shop.util.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class ConsoleInputTest {
    private static Scanner scanner;

    @AfterAll
    static void afterAll() {
        if (scanner != null)
            scanner.close();
    }

    @Test
    public void shouldAddProductToShopProductListUsingReflection() {
        Localization.loadLocalization(null);
        scanner = new Scanner("10^somefactory^400^200^13.03.2022^45.99^canned fish^".replace("^", System.lineSeparator()));
        Runner.setScanner(scanner);
        Product product = new CannedFood();
        ShopDAO dao = new ConsoleInput();
        dao.addNewProductReflection(product);
        List<Product> products = dao.getProductList();

        Product expected = new CannedFood(
                new BigDecimal("45.99"), "canned fish",
                400, 200,
                Util.stringToCalendar("13.03.2022", new SimpleDateFormat("dd.MM.yyyy")),
                10, "somefactory");
        Product actual = products.get(products.size() - 1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldAddRandomProductToShopProductListUsingReflection() {
        Localization.loadLocalization(null);
        Product product = new Furniture();
        ShopDAO dao = new RandomInput();
        List<Product> products = dao.getProductList();
        int before = products.size();
        dao.addNewProductReflection(product);

        int expected = before + 1;
        int actual = products.size();
        
        Assertions.assertEquals(expected, actual);
    }
}
