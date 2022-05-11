package com.webShop.repository.impl;

import com.webShop.entity.Product;
import com.webShop.entity.ProductsPageBean;
import com.webShop.repository.ProductsRepository;
import com.webShop.util.ProductsPageConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductsRepositoryImplTest {
    private static ProductsRepository productsRepository = new ProductsRepositoryImpl();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void shouldReturnProducts() throws SQLException {
        Product product1 = new Product("bnm", new BigDecimal("321"), "cat1", "prod1", "desk1", "img1");
        Product product2 = new Product("Abc", new BigDecimal("123"), "cat2", "prod2", "desk2", "img2");
        List<Product> expected = new ArrayList<>();
        expected.add(product1);
        expected.add(product2);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString(1)).thenReturn(product1.getName()).thenReturn(product2.getName());
        when(resultSet.getBigDecimal(2)).thenReturn(product1.getPrice()).thenReturn(product2.getPrice());
        when(resultSet.getString(3)).thenReturn(product1.getCategory()).thenReturn(product2.getCategory());
        when(resultSet.getString(4)).thenReturn(product1.getProducer()).thenReturn(product2.getProducer());
        when(resultSet.getString(5)).thenReturn(product1.getDescription()).thenReturn(product2.getDescription());
        when(resultSet.getString(6)).thenReturn(product1.getImage()).thenReturn(product2.getImage());
        ProductsPageBean bean = new ProductsPageBean();
        bean.setSortType(ProductsPageConfig.DEFAULT_SORT_OPTION);
        bean.setPage(1);
        bean.setPageSize(5);

        List<Product> actual = productsRepository.getProducts(bean, connection);

        boolean check = actual.get(0).equals(expected.get(0)) && actual.get(1).equals(expected.get(1));

        Assertions.assertTrue(check);
    }

    @Test
    public void shouldReturnCountProducts() throws SQLException {
        int expected = 2;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(2);

        int actual = productsRepository.countProducts(new ProductsPageBean(), connection);

        Assertions.assertEquals(expected, actual);
    }
}