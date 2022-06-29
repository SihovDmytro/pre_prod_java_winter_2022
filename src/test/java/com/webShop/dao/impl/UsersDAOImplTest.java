package com.webShop.dao.impl;

import com.webShop.dao.UsersDAO;
import com.webShop.entity.User;
import com.webShop.util.SQLCommands;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsersDAOImplTest {
    private static UsersDAO dao;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        dao = new UsersDAOImpl();
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void shouldReturnExistingUserWhenGetUserByLoginMethodCalled() throws SQLException {
        User user = new User("dmytro", "Dmytro", "Sihov",
                "abrakadabra", "Dmytro_Sihov@epam.com", false);
        when(connection.prepareStatement(SQLCommands.GET_USER_BY_LOGIN)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(2)).thenReturn(user.getLogin());
        when(resultSet.getString(3)).thenReturn(user.getName());
        when(resultSet.getString(4)).thenReturn(user.getSurname());
        when(resultSet.getString(5)).thenReturn(user.getPassword());
        when(resultSet.getString(6)).thenReturn(user.getEmail());
        when(resultSet.getBoolean(7)).thenReturn(user.isSendMail());

        User actual = dao.getUserByLogin("dmytro", connection).get();

        Assertions.assertEquals(user, actual);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenGetUserByLoginMethodCalledIfUserDoesNotExist() throws SQLException {
        when(connection.prepareStatement(SQLCommands.GET_USER_BY_LOGIN)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<User> user = dao.getUserByLogin("dmytro", connection);

        Assertions.assertFalse(user.isPresent());
    }
}