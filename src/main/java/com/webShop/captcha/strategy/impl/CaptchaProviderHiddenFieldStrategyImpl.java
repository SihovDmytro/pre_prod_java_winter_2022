package com.webShop.captcha.strategy.impl;

import com.webShop.captcha.strategy.CaptchaProviderStrategy;
import com.webShop.util.Attributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

public class CaptchaProviderHiddenFieldStrategyImpl extends CaptchaProviderStrategy {
    private static final Logger LOG = LogManager.getLogger(CaptchaProviderHiddenFieldStrategyImpl.class);

    @Override
    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        String captchaID = addCaptchaToMap(request, captcha);
        request.setAttribute(Attributes.CAPTCHA_ID, captchaID);
    }

    @Override
    public Optional<String> getCaptcha(HttpServletRequest request) {
        Map<String, String> captchaMap = getCaptchaMap(request);
        LOG.trace("captchaMap: " + captchaMap);

        Optional<String> captchaID = Optional.ofNullable(request.getParameter(Attributes.CAPTCHA_ID));
        Optional<String> foundCaptcha = getCaptchaFromMap(captchaID, request);
        LOG.trace("foundCaptcha: " + foundCaptcha);
        return foundCaptcha;
    }
}
