package com.webShop.service.impl;

import com.webShop.captcha.strategy.CaptchaProviderStrategy;
import com.webShop.service.CaptchaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CaptchaServiceImpl implements CaptchaService {
    private CaptchaProviderStrategy captchaProvider;

    public CaptchaServiceImpl(CaptchaProviderStrategy captchaProvider) {
        this.captchaProvider = captchaProvider;
    }

    @Override
    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        captchaProvider.addCaptcha(captcha, request, response);
    }

    @Override
    public Optional<String> getCaptcha(HttpServletRequest request) {
        return captchaProvider.getCaptcha(request);
    }
}
