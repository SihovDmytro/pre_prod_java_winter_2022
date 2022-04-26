package com.webShop.captcha.strategy;

import com.webShop.util.Attributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CaptchaProviderSession implements CaptchaProvider {
    private static final Logger LOG = LogManager.getLogger(CaptchaProviderSession.class);

    @Override
    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(Attributes.CAPTCHA, captcha);
    }

    @Override
    public String getCaptcha(HttpServletRequest request) {
        String foundCaptcha = (String) request.getSession().getAttribute(Attributes.CAPTCHA);
        request.getSession().removeAttribute(Attributes.CAPTCHA);
        LOG.trace("foundCaptcha: " + foundCaptcha);
        return foundCaptcha;
    }

}
