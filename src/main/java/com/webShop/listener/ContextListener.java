package com.webShop.listener;

import com.webShop.captcha.strategy.CaptchaProviderStrategy;
import com.webShop.captcha.strategy.impl.CaptchaProviderCookieStrategyImpl;
import com.webShop.captcha.strategy.impl.CaptchaProviderHiddenFieldStrategyImpl;
import com.webShop.captcha.strategy.impl.CaptchaProviderSessionStrategyImpl;
import com.webShop.dao.impl.UsersDAOImpl;
import com.webShop.entity.User;
import com.webShop.service.UsersService;
import com.webShop.service.impl.CaptchaServiceImpl;
import com.webShop.service.impl.UsersServiceImpl;
import com.webShop.util.Attributes;
import com.webShop.util.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final CaptchaProviderStrategy DEFAULT_CAPTCHA_PROVIDER = new CaptchaProviderSessionStrategyImpl();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String path = context.getRealPath(Constants.LOG_FILE_PATH);
        System.setProperty("logFile", path);

        String captchaProviderString = context.getInitParameter(Attributes.CAPTCHA_PROVIDER);
        CaptchaProviderStrategy captchaProvider = getCaptchaProviders().get(captchaProviderString);
        if (captchaProvider == null) {
            captchaProvider = DEFAULT_CAPTCHA_PROVIDER;
        }
        context.setAttribute(Attributes.CAPTCHA_SERVICE, new CaptchaServiceImpl(captchaProvider));

        List<User> users = new ArrayList<>();
        fillUsers(users);
        UsersService usersService = new UsersServiceImpl(new UsersDAOImpl(users));
        context.setAttribute(Attributes.USERS_SERVICE, usersService);
    }

    private Map<String, CaptchaProviderStrategy> getCaptchaProviders() {
        Map<String, CaptchaProviderStrategy> captchaProviderMap = new HashMap<>();
        captchaProviderMap.put("cookie", new CaptchaProviderCookieStrategyImpl());
        captchaProviderMap.put("hiddenField", new CaptchaProviderHiddenFieldStrategyImpl());
        captchaProviderMap.put("session", new CaptchaProviderSessionStrategyImpl());
        return captchaProviderMap;
    }

    private void fillUsers(List<User> users) {
        users.add(new User("dmytro", "Dmytro", "Sihov",
                "abrakadabra", "Dmytro_Sihov@epam.com", false));
        users.add(new User("vasya", "Vas", "Vass",
                "123456", "Vasya@epam.com", true));
        users.add(new User("petya", "Petya", "Pet",
                "qwerty", "Petya@epam.com", false));
        users.add(new User("dasdasasd", "adsasdasd", "asdasdasd",
                "asdasdasdas", "dasdasdda@epam.com", true));
    }
}
