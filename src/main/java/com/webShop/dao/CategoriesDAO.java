package com.webShop.dao;

import com.webShop.entity.Category;

import java.sql.Connection;
import java.util.List;

public interface CategoriesDAO {
    List<Category> getAllCategories(Connection connection);
}
