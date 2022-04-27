package com.webShop.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface CaptchaService {
    void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response);

    Optional<String> getCaptcha(HttpServletRequest request);
}
