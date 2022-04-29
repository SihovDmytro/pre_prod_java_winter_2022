package com.webShop.captcha.strategy.impl;

import com.webShop.captcha.strategy.CaptchaProviderStrategy;
import com.webShop.util.Attributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CaptchaProviderSessionStrategyImpl extends CaptchaProviderStrategy {
    private static final Logger LOG = LogManager.getLogger(CaptchaProviderSessionStrategyImpl.class);

    @Override
    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(Attributes.CAPTCHA, captcha);
    }

    @Override
    public Optional<String> getCaptcha(HttpServletRequest request) {
        Optional<String> foundCaptcha = Optional.ofNullable((String) request.getSession().getAttribute(Attributes.CAPTCHA));
        request.getSession().removeAttribute(Attributes.CAPTCHA);
        LOG.trace("foundCaptcha: " + foundCaptcha);
        return foundCaptcha;
    }
}
