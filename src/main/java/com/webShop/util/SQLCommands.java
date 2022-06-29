package com.webShop.util;

public class SQLCommands {
    public static final String GET_ALL_USERS = "SELECT * FROM user";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    public static final String INSERT_USER="INSERT INTO user (login, name, surname, password, email, sendMail) VALUES (?, ?, ?, ?, ?, ?)";
}
