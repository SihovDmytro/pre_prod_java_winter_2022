package com.webShop.repository.impl;

import com.task1.subtask2.ArrayList;
import com.webShop.entity.Product;
import com.webShop.entity.ProductsPageBean;
import com.webShop.repository.ProductsRepository;
import com.webShop.util.SQLCommands;
import com.webShop.util.SQLGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryImpl implements ProductsRepository {
    private static final Logger LOG = LogManager.getLogger(ProductsRepositoryImpl.class);

    @Override
    public List<Product> getProducts(ProductsPageBean bean, Connection connection) {
        List<Product> products = new ArrayList<>();
        String query = SQLGenerator.getProductsQuery(bean);
        LOG.trace("query: " + query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int i = setFilters(statement, bean);
            setLimit(statement, bean, i);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(unmap(resultSet));
                }
            }
        } catch (SQLException exception) {
            LOG.error("Cannot get all users", exception);
        }
        return products;
    }

    @Override
    public int countProducts(ProductsPageBean bean, Connection connection) {
        String query = SQLGenerator.countProductsQuery(bean);
        int count = 0;
        LOG.trace("query: " + query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setFilters(statement, bean);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException exception) {
            LOG.error("Cannot get all users", exception);
        }
        return count;
    }

    @Override
    public Optional<Product> getProduct(int id, Connection connection) {
        Optional<Product> product = Optional.empty();
        String query = SQLCommands.GET_PRODUCT_BY_ID;
        LOG.trace("query: " + query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product = Optional.of(unmap(resultSet));
                }
            }
        } catch (SQLException exception) {
            LOG.error("Cannot get all users", exception);
        }
        return product;
    }

    private static int setFilters(PreparedStatement statement, ProductsPageBean bean) throws SQLException {
        int i = 1;
        if (!bean.isFiltersEmpty()) {
            if (bean.getName() != null) {
                statement.setString(i++, bean.getName() + "%");
            }
            if (bean.getMinPrice() != null) {
                statement.setBigDecimal(i++, bean.getMinPrice());
            }
            if (bean.getMaxPrice() != null) {
                statement.setBigDecimal(i++, bean.getMaxPrice());
            }
            if (bean.getCategory() != null) {
                statement.setString(i++, bean.getCategory());
            }
            if (bean.getProducers() != null) {
                for (String producer : bean.getProducers()) {
                    statement.setString(i++, producer);
                }
            }
        }
        return i;
    }

    private static void setLimit(PreparedStatement statement, ProductsPageBean bean, int index) throws SQLException {
        int count = bean.getPageSize();
        int from = (bean.getPage() - 1) * count;

        statement.setInt(index++, from);
        statement.setInt(index++, count);
    }

    private static Product unmap(ResultSet resultSet) throws SQLException {
        int i = 1;
        Product product = new Product();
        product.setId(resultSet.getInt(i++));
        product.setName(resultSet.getString(i++));
        product.setPrice(resultSet.getBigDecimal(i++));
        product.setCategory(resultSet.getString(i++));
        product.setProducer(resultSet.getString(i++));
        product.setDescription(resultSet.getString(i++));
        product.setImage(resultSet.getString(i));
        return product;
    }
}
