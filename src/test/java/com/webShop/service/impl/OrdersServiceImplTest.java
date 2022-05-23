package com.webShop.service.impl;

import com.webShop.dao.impl.OrdersDAOImpl;
import com.webShop.dao.impl.UsersDAOImpl;
import com.webShop.entity.*;
import com.webShop.service.OrdersService;
import com.webShop.transaction.impl.TransactionManagerImpl;
import com.webShop.util.SQLCommands;
import com.webShop.util.SQLGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrdersServiceImplTest {
    private Connection connection;
    private DataSource dataSource;
    private PreparedStatement preparedStatement;
    private PreparedStatement preparedStatement2;
    private ResultSet resultSet;
    private OrdersService ordersService;


    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class);
        ordersService = new OrdersServiceImpl(new TransactionManagerImpl(dataSource), new OrdersDAOImpl());
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        preparedStatement2 = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void shouldAddNewOrder() throws SQLException {
        List<ProductInfo> products = new ArrayList<>();
        products.add(new ProductInfo(new Product(1, "bnm", new BigDecimal("321"), "cat1", "prod1", "desk1", "img1"), 2, new BigDecimal("1000")));
        products.add(new ProductInfo(new Product(2, "Abc", new BigDecimal("123"), "cat2", "prod2", "desk2", "img2"), 1, new BigDecimal("2000")));
        Order order = new Order(OrderStatus.CANCELED,
                "description", Calendar.getInstance(),
                new User("dmytro", "Dmytro", "Sihov", "abrakadabra", "Dmytro_Sihov@epam.com", false),
                products);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(SQLCommands.INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        when(connection.prepareStatement(SQLCommands.INSERT_LIST_ORDERS)).thenReturn(preparedStatement2).thenReturn(preparedStatement2);
        when(preparedStatement2.executeUpdate()).thenReturn(1).thenReturn(1);

        ordersService.add(order);

        verify(connection, times(1)).commit();
    }

    @Test
    public void shouldNotAddNewOrder() throws SQLException {
        List<ProductInfo> products = new ArrayList<>();
        products.add(new ProductInfo(new Product(1, "bnm", new BigDecimal("321"), "cat1", "prod1", "desk1", "img1"), 2, new BigDecimal("1000")));
        products.add(new ProductInfo(new Product(2, "Abc", new BigDecimal("123"), "cat2", "prod2", "desk2", "img2"), 1, new BigDecimal("2000")));
        Order order = new Order(OrderStatus.CANCELED,
                "description", Calendar.getInstance(),
                new User("dmytro", "Dmytro", "Sihov", "abrakadabra", "Dmytro_Sihov@epam.com", false),
                products);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(SQLCommands.INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        when(connection.prepareStatement(SQLCommands.INSERT_LIST_ORDERS)).thenReturn(preparedStatement2);
        when(preparedStatement2.executeUpdate()).thenReturn(0);

        ordersService.add(order);

        verify(connection, times(0)).commit();
        verify(connection, times(1)).rollback();
    }

}