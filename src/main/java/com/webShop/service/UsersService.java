package com.webShop.service;

import com.webShop.entity.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<User> getAllUsers();

    Optional<User> getUserByLogin(String login);

    void addUser(User user);
}
