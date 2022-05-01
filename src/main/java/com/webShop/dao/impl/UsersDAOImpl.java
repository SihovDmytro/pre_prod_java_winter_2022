package com.webShop.dao.impl;

import com.task1.subtask2.ArrayList;
import com.webShop.dao.UsersDAO;
import com.webShop.entity.User;
import com.webShop.util.SQLCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UsersDAOImpl implements UsersDAO {
    private static final Logger LOG = LogManager.getLogger(UsersDAOImpl.class);

    @Override
    public List<User> getAllUsers(Connection connection) {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLCommands.GET_ALL_USERS)) {
            while (resultSet.next()) {
                users.add(unmap(resultSet));
            }
        } catch (SQLException exception) {
            LOG.error("Cannot get all users", exception);
        }
        return users;
    }

    @Override
    public Optional<User> getUserByLogin(String login, Connection connection) {
        Optional<User> user = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLCommands.GET_USER_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = Optional.of(unmap(resultSet));
                }
            }
        } catch (SQLException exception) {
            LOG.error("Cannot get user by login", exception);
        }
        return user;
    }

    @Override
    public boolean addUser(User user, Connection connection) {
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(SQLCommands.INSERT_USER)) {
            map(statement, user);

            if (statement.executeUpdate() != 1) {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            LOG.error("Cannot get user by login", exception);
            result = false;
        }
        return result;
    }

    private void map(PreparedStatement preparedStatement, User user) throws SQLException {
        int i = 1;
        preparedStatement.setString(i++, user.getLogin());
        preparedStatement.setString(i++, user.getName());
        preparedStatement.setString(i++, user.getSurname());
        preparedStatement.setString(i++, user.getPassword());
        preparedStatement.setString(i++, user.getEmail());
        preparedStatement.setBoolean(i, user.isSendMail());
    }

    private User unmap(ResultSet resultSet) throws SQLException {
        int i = 2;
        User user = new User();
        user.setLogin(resultSet.getString(i++));
        user.setName(resultSet.getString(i++));
        user.setSurname(resultSet.getString(i++));
        user.setPassword(resultSet.getString(i++));
        user.setEmail(resultSet.getString(i++));
        user.setSendMail(resultSet.getBoolean(i));
        return user;
    }
}
