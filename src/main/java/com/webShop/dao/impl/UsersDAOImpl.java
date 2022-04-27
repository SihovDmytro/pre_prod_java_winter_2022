package com.webShop.dao.impl;

import com.webShop.dao.UsersDAO;
import com.webShop.entity.User;

import java.util.List;
import java.util.Optional;

public class UsersDAOImpl implements UsersDAO {

    private List<User> users;

    public UsersDAOImpl(List<User> users) {
        this.users = users;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }
}
