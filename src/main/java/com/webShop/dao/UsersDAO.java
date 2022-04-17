package com.webShop.dao;

import com.webShop.entity.User;

import java.util.List;

public interface UsersDAO {
    List<User> getAllUsers();
    User getUserByLogin(String login);
    void addUser(User user);
}
