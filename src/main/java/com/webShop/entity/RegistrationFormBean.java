package com.webShop.entity;

import javax.servlet.http.Part;

public class RegistrationFormBean {
    private String login;
    private String name;
    private String surname;
    private String password;
    private String passwordRepeat;
    private String email;
    private boolean sendMail;
    private String userCaptcha;
    private Part avatar;

    public RegistrationFormBean(String login, String name, String sName, String password, String passwordRepeat, String email, boolean sendMail, String userCaptcha, Part avatar) {
        this.login = login;
        this.name = name;
        this.surname = sName;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.email = email;
        this.sendMail = sendMail;
        this.userCaptcha = userCaptcha;
        this.avatar = avatar;
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

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
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

    public String getUserCaptcha() {
        return userCaptcha;
    }

    public void setUserCaptcha(String userCaptcha) {
        this.userCaptcha = userCaptcha;
    }

    public Part getAvatar() {
        return avatar;
    }

    public void setAvatar(Part avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "RegistrationFormBean{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", passwordRepeat='" + passwordRepeat + '\'' +
                ", email='" + email + '\'' +
                ", sendMail=" + sendMail +
                ", userCaptcha='" + userCaptcha + '\'' +
                ", avatar=" + avatar +
                '}';
    }
}
