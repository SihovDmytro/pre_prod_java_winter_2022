package com.webShop.captcha.strategy;

import com.webShop.util.Attributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class CaptchaProviderStrategy {
    public abstract void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response);

    public abstract Optional<String> getCaptcha(HttpServletRequest request);

    protected Map<String, String> getCaptchaMap(HttpServletRequest request) {
        Map<String, String> captchaMap = (Map<String, String>) request.getServletContext().getAttribute(Attributes.CAPTCHA_MAP);
        if (captchaMap == null) {
            captchaMap = new HashMap<>();
            request.getServletContext().setAttribute(Attributes.CAPTCHA_MAP, captchaMap);
        }
        return captchaMap;
    }
}
