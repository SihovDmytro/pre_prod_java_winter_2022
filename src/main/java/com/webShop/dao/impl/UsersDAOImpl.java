package com.webShop.dao.impl;

import com.webShop.dao.UsersDAO;
import com.webShop.entity.User;

import java.util.List;

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
    public User getUserByLogin(String login) {
        User foundUser = null;
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                foundUser = user;
                break;
            }
        }
        return foundUser;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }
}
