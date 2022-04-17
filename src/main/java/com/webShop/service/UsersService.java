package com.webShop.service;

import com.webShop.entity.User;

import java.util.List;

public interface UsersService {
    List<User> getAllUsers();
    User getUserByLogin(String login);
    void addUser(User user);
}
