package com.webShop.util;

import com.webShop.captcha.CaptchaSettings;
import com.webShop.entity.LoginFormBean;
import com.webShop.entity.RegistrationFormBean;
import com.webShop.service.CaptchaService;
import com.webShop.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Validator {

    public static Map<String, String> validateLogin(LoginFormBean bean, UsersService usersService, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        if (!validateLogin(bean.getLogin())) {
            errors.put(Parameters.LOGIN, Messages.INVALID_LOGIN);
        }
        if (!validatePassword(bean.getPassword())) {
            errors.put(Parameters.PASSWORD, Messages.INVALID_PASSWORD);
        }
        if (errors.isEmpty() && !usersService.login(bean.getLogin(), bean.getPassword())) {
            errors.put(Attributes.CREDENTIALS, Messages.LOGIN_FAIL);
        }
        return errors;
    }

    public static Map<String, String> validateRegistration(RegistrationFormBean bean, UsersService usersService, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        if (isPageExpired(request)) {
            errors.put(Attributes.PAGE_GENERATION_TIME, Messages.EXPIRED_PAGE);
        }
        if (!validateLogin(bean.getLogin())) {
            errors.put(Parameters.LOGIN, Messages.INVALID_LOGIN);
        } else if (usersService.getUserByLogin(bean.getLogin()).isPresent()) {
            errors.put(Parameters.LOGIN, Messages.LOGIN_EXISTS);
        }
        if (!validateCaptcha(bean.getUserCaptcha(), request)) {
            errors.put(Parameters.USER_CAPTCHA, Messages.WRONG_CAPTCHA);
        }
        if (!validatePassword(bean.getPassword())) {
            errors.put(Parameters.PASSWORD, Messages.INVALID_PASSWORD);
        }
        if (!validatePasswordRepeat(bean.getPassword(), bean.getPasswordRepeat())) {
            errors.put(Parameters.REPEAT_PASSWORD, Messages.INVALID_PASSWORD_REPEAT);
        }
        if (!validateEmail(bean.getEmail())) {
            errors.put(Parameters.EMAIL, Messages.INVALID_EMAIL);
        }
        if (!validateName(bean.getName())) {
            errors.put(Parameters.NAME, Messages.INVALID_NAME);
        }
        if (!validateSurname(bean.getSurname())) {
            errors.put(Parameters.SURNAME, Messages.INVALID_SURNAME);
        }
        if (!validateAvatar(bean.getAvatar())) {
            errors.put(Parameters.AVATAR, Messages.INVALID_AVATAR);
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

    private static boolean validateCaptcha(String captcha, HttpServletRequest request) {
        CaptchaService captchaService = (CaptchaService) request.getServletContext().getAttribute(Attributes.CAPTCHA_SERVICE);
        Optional<String> generatedCaptcha = captchaService.getCaptcha(request);
        return generatedCaptcha.isPresent() && generatedCaptcha.get().equals(captcha);
    }

    private static boolean isPageExpired(HttpServletRequest request) {
        long generationTime = (Long) request.getSession().getAttribute(Attributes.PAGE_GENERATION_TIME);
        return System.currentTimeMillis() - generationTime > CaptchaSettings.MAX_INTERVAL;
    }

    private static boolean validateAvatar(Part avatar) {
        return isPartEmpty(avatar) || (avatar.getSize() <= AvatarConfig.MAX_FILE_SIZE &&
                avatar.getContentType().toLowerCase().startsWith(Constants.IMAGE));
    }

    public static boolean isPartEmpty(Part part) {
        return part.getSubmittedFileName().equals("") || part.getSize() == 0;
    }
}
