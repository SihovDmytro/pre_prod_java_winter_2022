package com.webShop.util;

import com.webShop.entity.RegistrationFormBean;
import com.webShop.service.UsersService;

import java.util.HashMap;
import java.util.Map;

public class Validator {
    public static Map<String, String> validateRegistration(RegistrationFormBean bean, UsersService usersService) {
        Map<String, String> errors = new HashMap<>();
        if (!validateLogin(bean.getLogin())) {
            errors.put(Attributes.LOGIN, Messages.INVALID_LOGIN);
        } else if (usersService.getUserByLogin(bean.getLogin()) != null) {
            errors.put(Attributes.LOGIN, Messages.LOGIN_EXISTS);
        }
        if (!validatePassword(bean.getPassword())) {
            errors.put(Attributes.PASSWORD, Messages.INVALID_PASSWORD);
        }
        if (!validatePasswordRepeat(bean.getPassword(), bean.getPasswordRepeat())) {
            errors.put(Attributes.REPEAT_PASSWORD, Messages.INVALID_PASSWORD_REPEAT);
        }
        if (!validateEmail(bean.getEmail())) {
            errors.put(Attributes.EMAIL, Messages.INVALID_EMAIL);
        }
        if (!validateName(bean.getName())) {
            errors.put(Attributes.NAME, Messages.INVALID_NAME);
        }
        if (!validateSurname(bean.getSurname())) {
            errors.put(Attributes.SURNAME, Messages.INVALID_SURNAME);
        }
        return errors;
    }

    private static boolean validateLogin(String login) {
        String loginPattern = "^[\\w_-]{3,20}$";
        return login != null && login.length() <= 20 && login.length() >= 3 && login.matches(loginPattern);
    }

    private static boolean validatePassword(String password) {
        return password != null && password.length() <= 64 && password.length() >= 6;
    }

    private static boolean validatePasswordRepeat(String password, String repeat) {
        return repeat != null && password != null && repeat.length() <= 64 && repeat.length() >= 6 && password.equals(repeat);
    }

    private static boolean validateEmail(String email) {
        String emailPattern = "[\\w_\\-.]+@([\\w-]+\\.)+[\\w-]{1,5}$";
        return email != null && email.length() <= 30 && email.length() >= 5 && email.matches(emailPattern);
    }

    private static boolean validateName(String name) {
        return name != null && name.length() <= 30 && name.length() >= 1;
    }

    private static boolean validateSurname(String surname) {
        return surname != null && surname.length() <= 30 && surname.length() >= 1;
    }

}
