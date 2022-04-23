package com.webShop.listener;

import com.webShop.captcha.CaptchaProvider;
import com.webShop.captcha.CaptchaProviderCookie;
import com.webShop.captcha.CaptchaProviderHiddenField;
import com.webShop.captcha.CaptchaProviderSession;
import com.webShop.util.Attributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final CaptchaProvider DEFAULT_CAPTCHA_PROVIDER = new CaptchaProviderSession();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String path = context.getRealPath("/WEB-INF/logs/webShop.log");
        System.setProperty("logFile", path);

        String captchaProviderString = context.getInitParameter(Attributes.CAPTCHA_PROVIDER);
        CaptchaProvider captchaProvider = getCaptchaProviders().get(captchaProviderString);
        if (captchaProvider == null) {
            captchaProvider = DEFAULT_CAPTCHA_PROVIDER;
        }
        context.setAttribute(Attributes.CAPTCHA_PROVIDER, captchaProvider);

    }

    private Map<String, CaptchaProvider> getCaptchaProviders() {
        Map<String, CaptchaProvider> captchaProviderMap = new HashMap<>();
        captchaProviderMap.put("cookie", new CaptchaProviderCookie());
        captchaProviderMap.put("hiddenField", new CaptchaProviderHiddenField());
        captchaProviderMap.put("session", new CaptchaProviderSession());
        return captchaProviderMap;
    }
}
