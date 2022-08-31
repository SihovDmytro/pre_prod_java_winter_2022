package com.webShop.dao;

import com.webShop.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UsersDAO {
    List<User> getAllUsers(Connection connection);

    Optional<User> getUserByLogin(String login, Connection connection);

    boolean addUser(User user, Connection connection) throws SQLException;
}
