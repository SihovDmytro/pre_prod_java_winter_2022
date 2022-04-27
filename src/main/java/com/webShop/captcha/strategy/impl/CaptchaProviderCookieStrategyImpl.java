package com.webShop.captcha.strategy.impl;

import com.webShop.captcha.strategy.CaptchaProviderStrategy;
import com.webShop.util.Attributes;
import com.webShop.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

public class CaptchaProviderCookieStrategyImpl extends CaptchaProviderStrategy {
    private static final Logger LOG = LogManager.getLogger(CaptchaProviderCookieStrategyImpl.class);

    @Override
    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> captchaMap = getCaptchaMap(request);

        String captchaID = String.valueOf(RandomUtil.generateLong());
        captchaMap.put(captchaID, captcha);
        Cookie cookie = new Cookie(Attributes.CAPTCHA_ID, captchaID);
        response.addCookie(cookie);
    }

    @Override
    public Optional<String> getCaptcha(HttpServletRequest request) {
        Map<String, String> captchaMap = getCaptchaMap(request);
        LOG.trace("captchaMap: " + captchaMap);

        Optional<String> captchaIDOptional = getCaptchaID(request.getCookies());
        Optional<String> foundCaptcha = Optional.empty();

        if (captchaIDOptional.isPresent()) {
            String captchaID = captchaIDOptional.get();
            LOG.debug("captchaID: " + captchaID);
            foundCaptcha = Optional.ofNullable(captchaMap.get(captchaID));
            captchaMap.remove(captchaID);
        }

        LOG.debug("foundCaptcha: " + foundCaptcha);
        return foundCaptcha;
    }

    private static Optional<String> getCaptchaID(Cookie[] cookies) {
        for (Cookie tempCookie : cookies) {
            if (tempCookie.getName().equals(Attributes.CAPTCHA_ID)) {
                return Optional.ofNullable(tempCookie.getValue());
            }
        }
        return Optional.empty();
    }
}
