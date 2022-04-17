package com.webShop.entity;

import java.util.Objects;

public class User {
    private String login;
    private String name;
    private String surname;
    private String password;
    private String email;
    private boolean sendMail;

    public User(RegistrationFormBean bean) {
        this.login = bean.getLogin();
        this.name = bean.getName();
        this.surname = bean.getSurname();
        this.password = bean.getPassword();
        this.email = bean.getEmail();
        this.sendMail = bean.isSendMail();
    }

    public User(String login, String name, String sName, String password, String email, boolean sendMail) {
        this.login = login;
        this.name = name;
        this.surname = sName;
        this.password = password;
        this.email = email;
        this.sendMail = sendMail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSendMail() {
        return sendMail;
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return sendMail == user.sendMail &&
                Objects.equals(login, user.login) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, name, surname, password, email, sendMail);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", sendMail=" + sendMail +
                '}';
    }
}
