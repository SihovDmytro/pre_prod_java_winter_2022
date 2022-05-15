package com.webShop.entity;

import java.util.Calendar;
import java.util.List;

public class Order {
    private int orderID;
    private OrderStatus status;
    private String statusDescription;
    private Calendar date;
    private User user;
    private List<ProductInfo> products;

    public Order(int orderID, OrderStatus status, String statusDescription, Calendar date, User user, List<ProductInfo> products) {
        this.orderID = orderID;
        this.status = status;
        this.statusDescription = statusDescription;
        this.date = date;
        this.user = user;
        this.products = products;
    }

    public Order(OrderStatus status, String statusDescription, Calendar date, User user, List<ProductInfo> products) {
        this.status = status;
        this.statusDescription = statusDescription;
        this.date = date;
        this.user = user;
        this.products = products;
    }

    public int getOrderID() {
        return orderID;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public Calendar getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public List<ProductInfo> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", status=" + status +
                ", statusDescription='" + statusDescription + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", products=" + products +
                '}';
    }
}
