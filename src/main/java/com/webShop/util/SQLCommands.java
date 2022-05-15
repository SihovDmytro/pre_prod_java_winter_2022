package com.webShop.util;

public class SQLCommands {
    public static final String GET_ALL_USERS = "SELECT * FROM user";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    public static final String INSERT_USER = "INSERT INTO user (login, name, surname, password, email, sendMail) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_CATEGORIES = "SELECT name FROM category";
    public static final String GET_ALL_PRODUCERS = "SELECT name FROM producer";
    public static final String GET_ALL_PRODUCTS = "SELECT product.productID, product.name, product.price, category.name, producer.name, product.description, product.image " +
            "from product join category on product.categoryID=category.categoryID " +
            "join producer on product.producerID=producer.producerID";
    public static final String COUNT_PRODUCTS = "SELECT count(*) " +
            "from product join category on product.categoryID=category.categoryID " +
            "join producer on product.producerID=producer.producerID";
    public static final String INSERT_ORDER="insert into orders (status, statusDescription, date, userID) values (?, ?, ?, (select userID from user where login=?))";
    public static final String INSERT_LIST_ORDERS="insert into list_orders values(?, ?, ?)";
}
