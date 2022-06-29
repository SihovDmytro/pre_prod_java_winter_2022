package com.webShop.service.impl;

import com.webShop.dao.UsersDAO;
import com.webShop.entity.User;
import com.webShop.service.UsersService;
import com.webShop.transaction.TransactionManager;

import java.util.List;
import java.util.Optional;

public class UsersServiceImpl implements UsersService {
    private TransactionManager transactionManager;
    private UsersDAO usersDAO;

    public UsersServiceImpl(TransactionManager transactionManager, UsersDAO usersDAO) {
        this.transactionManager = transactionManager;
        this.usersDAO = usersDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return transactionManager.doInTransaction((connection -> usersDAO.getAllUsers(connection)));
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        return transactionManager.doInTransaction((connection -> usersDAO.getUserByLogin(login, connection)));
    }

    @Override
    public void addUser(User user) {
        transactionManager.doInTransaction((connection -> usersDAO.addUser(user, connection)));
    }

    @Override
    public boolean login(String login, String password) {
        return transactionManager.doInTransaction((connection -> {
            Optional<User> user = usersDAO.getUserByLogin(login, connection);
            return user.isPresent() && user.get().getPassword().equals(password);
        }));
    }
}
