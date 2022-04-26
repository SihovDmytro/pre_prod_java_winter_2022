package com.webShop.captcha.strategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaProvider {
    void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response);

    String getCaptcha(HttpServletRequest request);
}
