package com.webShop.captcha.strategy;

import com.webShop.util.Attributes;
import com.webShop.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CaptchaProviderCookie implements CaptchaProvider {
    private static final Logger LOG = LogManager.getLogger(CaptchaProviderCookie.class);

    @Override
    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> captchaMap = (Map<String, String>) request.getServletContext().getAttribute(Attributes.CAPTCHA_MAP);

        if (captchaMap == null) {
            captchaMap = new HashMap<>();
            request.getServletContext().setAttribute(Attributes.CAPTCHA_MAP, captchaMap);
        }

        String captchaID = String.valueOf(RandomUtil.generateLong());
        captchaMap.put(captchaID, captcha);
        Cookie cookie = new Cookie(Attributes.CAPTCHA_ID, captchaID);
        response.addCookie(cookie);
    }

    @Override
    public String getCaptcha(HttpServletRequest request) {
        Map<String, String> captchaMap = (Map<String, String>) request.getServletContext().getAttribute(Attributes.CAPTCHA_MAP);
        LOG.trace("captchaMap: " + captchaMap);
        Cookie[] cookies = request.getCookies();
        LOG.trace("cookies: " + cookies);
        Cookie cookie = null;

        for (Cookie tempCookie : cookies) {
            if (tempCookie.getName().equals(Attributes.CAPTCHA_ID)) {
                cookie = tempCookie;
                break;
            }
        }

        LOG.trace("cookie: " + cookie);
        String foundCaptcha = null;
        if (captchaMap == null || cookie == null) {
            return foundCaptcha;
        }

        try {
            String captchaID = cookie.getValue();
            LOG.trace("captchaID: " + captchaID);
            foundCaptcha = captchaMap.get(captchaID);
            captchaMap.remove(captchaID);
        } catch (NumberFormatException exception) {
            LOG.error("Cannot parse cookie value", exception);
        }
        LOG.debug("foundCaptcha: " + foundCaptcha);
        return foundCaptcha;
    }
}
