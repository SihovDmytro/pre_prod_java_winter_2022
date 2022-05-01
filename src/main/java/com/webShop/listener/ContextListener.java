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
import com.webShop.transaction.TransactionManager;
import com.webShop.transaction.impl.TransactionManagerImpl;
import com.webShop.util.Attributes;
import com.webShop.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final CaptchaProviderStrategy DEFAULT_CAPTCHA_PROVIDER = new CaptchaProviderSessionStrategyImpl();
    private static final Logger LOG = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSource dataSource = null;
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/webshop");
        } catch (NamingException exception) {
            LOG.error("Cannot get datasource", exception);
        }

        ServletContext context = sce.getServletContext();

        String captchaProviderString = context.getInitParameter(Attributes.CAPTCHA_PROVIDER);
        LOG.debug("captchaProviderString: " + captchaProviderString);
        CaptchaProviderStrategy captchaProvider = getCaptchaProviders().get(captchaProviderString);
        if (captchaProvider == null) {
            captchaProvider = DEFAULT_CAPTCHA_PROVIDER;
        }
        LOG.debug("captchaProvider: " + captchaProvider);
        context.setAttribute(Attributes.CAPTCHA_SERVICE, new CaptchaServiceImpl(captchaProvider));

        TransactionManager transactionManager = new TransactionManagerImpl(dataSource);
        UsersService usersService = new UsersServiceImpl(transactionManager, new UsersDAOImpl());
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
