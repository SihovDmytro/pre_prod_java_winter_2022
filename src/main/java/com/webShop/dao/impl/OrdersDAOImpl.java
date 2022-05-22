package com.webShop.dao.impl;

import com.webShop.dao.OrdersDAO;
import com.webShop.entity.Order;
import com.webShop.entity.ProductInfo;
import com.webShop.util.SQLCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class OrdersDAOImpl implements OrdersDAO {
    private static final Logger LOG = LogManager.getLogger(OrdersDAOImpl.class);

    @Override
    public boolean add(Order order, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLCommands.INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            mapOrder(statement, order);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                boolean result = resultSet.next() && addListOrders(order, connection, resultSet.getInt(1));
                if (!result) {
                    throw new SQLException();
                }
            }
        } catch (SQLException exception) {
            LOG.error("Cannot add order", exception);
            throw new SQLException();
        }
        return true;
    }

    private boolean addListOrders(Order order, Connection connection, int id) {
        boolean result = true;
        for (ProductInfo productInfo : order.getProducts()) {
            try (PreparedStatement statement = connection.prepareStatement(SQLCommands.INSERT_LIST_ORDERS)) {
                mapListOrders(statement, productInfo, id);
                int rows = statement.executeUpdate();
                if (rows != 1) {
                    result = false;
                }
            } catch (SQLException exception) {
                LOG.error("Cannot add list orders", exception);
            }
        }
        return result;
    }

    private void mapOrder(PreparedStatement statement, Order order) throws SQLException {
        int i = 1;
        statement.setString(i++, order.getStatus().toString());
        statement.setString(i++, order.getStatusDescription());
        statement.setTimestamp(i++, new Timestamp(order.getDate().getTimeInMillis()));
        statement.setString(i++, order.getUser().getLogin());
    }

    private void mapListOrders(PreparedStatement statement, ProductInfo product, int id) throws SQLException {
        int i = 1;
        statement.setInt(i++, id);
        statement.setInt(i++, product.getProduct().getId());
        statement.setInt(i++, product.getNumber());
    }
}
