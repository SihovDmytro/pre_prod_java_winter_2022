package com.webShop.util;

import com.webShop.entity.ProductsPageBean;
import com.webShop.entity.SortOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

class SQLGeneratorTest {
    private static final String PRODUCTS_QUERY_WITHOUT_FILTERS = "SELECT product.name, product.price, category.name, producer.name, product.description, product.image " +
            "from product join category on product.categoryID=category.categoryID " +
            "join producer on product.producerID=producer.producerID " +
            "order by product.price DESC " +
            "limit ?,?";
    private static final String COUNT_PRODUCTS_QUERY_ALL_RESTRICTIONS = "SELECT count(*) " +
            "from product join category on product.categoryID=category.categoryID " +
            "join producer on product.producerID=producer.producerID " +
            "where product.name like ? " +
            "and product.price >= ? " +
            "and product.price <= ? " +
            "and category.name = ? " +
            "and ( producer.name = ? or producer.name = ?)";
    private static final String PRODUCTS_QUERY_ALL_RESTRICTIONS = "SELECT product.name, product.price, category.name, producer.name, product.description, product.image " +
            "from product join category on product.categoryID=category.categoryID " +
            "join producer on product.producerID=producer.producerID " +
            "where product.name like ? " +
            "and product.price >= ? " +
            "and product.price <= ? " +
            "and category.name = ? " +
            "and ( producer.name = ? or producer.name = ?) " +
            "order by product.price DESC " +
            "limit ?,?";

    public ProductsPageBean getBean() {
        BigDecimal minPrice = new BigDecimal(Mockito.anyInt());
        BigDecimal maxPrice = new BigDecimal(Mockito.anyInt());
        String name = Mockito.anyString();
        String category = Mockito.anyString();
        String[] producers = new String[]{Mockito.anyString(), Mockito.anyString()};
        int pageSize = Mockito.anyInt();
        int page = Mockito.anyInt();
        SortOption sortType = ProductsPageConfig.DEFAULT_SORT_OPTION;
        return new ProductsPageBean(minPrice, maxPrice, name, category, producers, pageSize, page, sortType);
    }

    @Test
    public void shouldGenerateGetProductsQueryUsingAllOptions() {

        String expected = PRODUCTS_QUERY_ALL_RESTRICTIONS;
        String actual = SQLGenerator.getProductsQuery(getBean());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerateCountProductsQueryUsingAllOptions() {

        String expected = COUNT_PRODUCTS_QUERY_ALL_RESTRICTIONS;
        String actual = SQLGenerator.countProductsQuery(getBean());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerateGetProductsQueryWithoutFilters() {

        String expected = PRODUCTS_QUERY_WITHOUT_FILTERS;

        String actual = SQLGenerator.getProductsQuery(new ProductsPageBean());

        Assertions.assertEquals(expected, actual);
    }

}