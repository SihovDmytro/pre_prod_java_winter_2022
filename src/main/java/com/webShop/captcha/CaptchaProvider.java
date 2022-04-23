package com.webShop.captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaProvider {
    void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response);
    boolean checkCaptcha(String captcha, HttpServletRequest request);
}
