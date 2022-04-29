package com.webShop.service.impl;

import com.webShop.dao.UsersDAO;
import com.webShop.entity.User;
import com.webShop.service.UsersService;

import java.util.List;
import java.util.Optional;

public class UsersServiceImpl implements UsersService {
    private UsersDAO usersDAO;

    public UsersServiceImpl(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return usersDAO.getAllUsers();
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        return usersDAO.getUserByLogin(login);
    }

    @Override
    public void addUser(User user) {
        usersDAO.addUser(user);
    }
}
