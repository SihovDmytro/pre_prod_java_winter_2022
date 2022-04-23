package com.webShop.captcha;

import com.webShop.util.Attributes;
import com.webShop.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CaptchaProviderHiddenField implements CaptchaProvider {
    private static final Logger LOG = LogManager.getLogger(CaptchaProviderHiddenField.class);

    @Override
    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> captchaMap = (Map<String, String>) request.getServletContext().getAttribute(Attributes.CAPTCHA_MAP);

        if (captchaMap == null) {
            captchaMap = new HashMap<>();
            request.getServletContext().setAttribute(Attributes.CAPTCHA_MAP, captchaMap);
        }

        String captchaID = String.valueOf(RandomUtil.generateLong());
        captchaMap.put(captchaID, captcha);
        request.getSession().setAttribute(Attributes.CAPTCHA_ID, captchaID);
    }

    @Override
    public boolean checkCaptcha(String captcha, HttpServletRequest request) {
        Map<String, String> captchaMap = (Map<String, String>) request.getServletContext().getAttribute(Attributes.CAPTCHA_MAP);
        LOG.trace("captchaMap: "+captchaMap);

        if (captchaMap == null) {
            return false;
        }

        try {
            String captchaID = request.getParameter(Attributes.CAPTCHA_ID);
            LOG.trace("captchaID: "+captchaID);
            String foundCaptcha = captchaMap.get(captchaID);
            LOG.trace("foundCaptcha: "+foundCaptcha);
            return foundCaptcha.equals(captcha);
        } catch (NullPointerException exception) {
            LOG.error("Cannot parse attribute value", exception);
            return false;
        }
    }
}
