package com.webShop.dao.impl;

import com.task1.subtask2.ArrayList;
import com.webShop.dao.CategoriesDAO;
import com.webShop.entity.Category;
import com.webShop.util.SQLCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CategoriesDAOImpl implements CategoriesDAO {
    private static final Logger LOG = LogManager.getLogger(CategoriesDAOImpl.class);

    @Override
    public List<Category> getAllCategories(Connection connection) {
        List<Category> categories = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLCommands.GET_ALL_CATEGORIES)) {
            while (resultSet.next()) {
                categories.add(unmap(resultSet));
            }
        } catch (SQLException exception) {
            LOG.trace("Cannot get all categories");
        }
        return categories;
    }

    private Category unmap(ResultSet resultSet) throws SQLException {
        return new Category(resultSet.getString(1));
    }
}
