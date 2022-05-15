package com.webShop.util;

import com.webShop.entity.ProductsPageBean;
import com.webShop.entity.SortOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class SQLGeneratorTest {
    public ProductsPageBean getBean() {
        BigDecimal minPrice = new BigDecimal("10");
        BigDecimal maxPrice = new BigDecimal("100.99");
        String name = "asd das";
        String category = "zxc";
        String[] producers = new String[]{"producer 1", "producer 2"};
        int pageSize = 10;
        int page = 2;
        SortOption sortType = ProductsPageConfig.DEFAULT_SORT_OPTION;
        return new ProductsPageBean(minPrice, maxPrice, name, category, producers, pageSize, page, sortType);
    }

    @Test
    public void shouldGenerateGetProductsQueryUsingAllOptions() {

        String expected = SQLCommands.GET_ALL_PRODUCTS +
                " where product.name like ? " +
                "and product.price >= ? " +
                "and product.price <= ? " +
                "and category.name = ? " +
                "and ( producer.name = ? or producer.name = ?) " +
                "order by product.price DESC " +
                "limit ?,?";
        String actual = SQLGenerator.getProductsQuery(getBean());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerateCountProductsQueryUsingAllOptions() {

        String expected = SQLCommands.COUNT_PRODUCTS +
                " where product.name like ? " +
                "and product.price >= ? " +
                "and product.price <= ? " +
                "and category.name = ? " +
                "and ( producer.name = ? or producer.name = ?)";
        String actual = SQLGenerator.countProductsQuery(getBean());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerateGetProductsQueryWithoutFilters() {

        String expected = SQLCommands.GET_ALL_PRODUCTS +
                " order by product.price DESC " +
                "limit ?,?";

        String actual = SQLGenerator.getProductsQuery(new ProductsPageBean());

        Assertions.assertEquals(expected, actual);
    }

}