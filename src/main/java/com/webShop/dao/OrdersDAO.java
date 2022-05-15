package com.webShop.dao;

import com.webShop.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrdersDAO {
    boolean add(Order order, Connection connection) throws SQLException;
}
