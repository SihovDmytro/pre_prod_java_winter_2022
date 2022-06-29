package com.webShop.entity;

public class LoginFormBean {
    private String login;
    private String password;

    public LoginFormBean(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginFormBean{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
