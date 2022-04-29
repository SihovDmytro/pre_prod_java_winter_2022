package com.webShop.dao;

import com.webShop.entity.User;

import java.util.List;
import java.util.Optional;

public interface UsersDAO {
    List<User> getAllUsers();

    Optional<User> getUserByLogin(String login);

    void addUser(User user);
}
