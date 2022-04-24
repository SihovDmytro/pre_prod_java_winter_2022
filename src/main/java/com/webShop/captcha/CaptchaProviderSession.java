package com.webShop.captcha;

import com.webShop.util.Attributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CaptchaProviderSession implements CaptchaProvider {

    @Override
    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(Attributes.CAPTCHA, captcha);
    }

    @Override
    public boolean checkCaptcha(String captcha, HttpServletRequest request) {
        boolean result = captcha.equals(request.getSession().getAttribute(Attributes.CAPTCHA));
        request.getSession().removeAttribute(Attributes.CAPTCHA);
        return result;
    }

}
