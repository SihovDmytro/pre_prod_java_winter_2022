package com.webShop.listener;

import com.webShop.captcha.strategy.CaptchaProviderStrategy;
import com.webShop.captcha.strategy.impl.CaptchaProviderCookieStrategyImpl;
import com.webShop.captcha.strategy.impl.CaptchaProviderHiddenFieldStrategyImpl;
import com.webShop.captcha.strategy.impl.CaptchaProviderSessionStrategyImpl;
import com.webShop.dao.impl.CategoriesDAOImpl;
import com.webShop.dao.impl.OrdersDAOImpl;
import com.webShop.dao.impl.ProducersDAOImpl;
import com.webShop.dao.impl.UsersDAOImpl;
import com.webShop.entity.SortOption;
import com.webShop.entity.SortOrder;
import com.webShop.repository.impl.ProductsRepositoryImpl;
import com.webShop.service.CategoriesService;
import com.webShop.service.OrdersService;
import com.webShop.service.ProducersService;
import com.webShop.service.ProductsService;
import com.webShop.service.UsersService;
import com.webShop.service.impl.CaptchaServiceImpl;
import com.webShop.service.impl.CategoriesServiceImpl;
import com.webShop.service.impl.OrdersServiceImpl;
import com.webShop.service.impl.ProducersServiceImpl;
import com.webShop.service.impl.ProductsServiceImpl;
import com.webShop.service.impl.UsersServiceImpl;
import com.webShop.transaction.TransactionManager;
import com.webShop.transaction.impl.TransactionManagerImpl;
import com.webShop.util.Attributes;
import com.webShop.util.AvatarConfig;
import com.webShop.util.Constants;
import com.webShop.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.File;
import java.util.Arrays;
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
        String pathToAvatarsFolder = context.getRealPath(File.separator + AvatarConfig.AVATARS_FOLDER);
        LOG.trace("pathToAvatarsFolder: " + pathToAvatarsFolder);
        context.setAttribute(Attributes.AVATARS_FOLDER, pathToAvatarsFolder);

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
        CategoriesService categoriesService = new CategoriesServiceImpl(transactionManager, new CategoriesDAOImpl());
        context.setAttribute(Attributes.CATEGORIES_SERVICE, categoriesService);
        ProducersService producersService = new ProducersServiceImpl(transactionManager, new ProducersDAOImpl());
        context.setAttribute(Attributes.PRODUCERS_SERVICE, producersService);
        ProductsService productsService = new ProductsServiceImpl(transactionManager, new ProductsRepositoryImpl());
        context.setAttribute(Attributes.PRODUCTS_SERVICE, productsService);
        OrdersService ordersService = new OrdersServiceImpl(transactionManager, new OrdersDAOImpl());
        context.setAttribute(Attributes.ORDERS_SERVICE, ordersService);

        List<Integer> pageSizes = getPageSizes();
        LOG.debug("pageSizes: " + pageSizes);
        context.setAttribute(Attributes.PAGE_SIZE, pageSizes);

        List<SortOption> sortOptions = getSortOptions();
        LOG.debug("sortOptions: " + sortOptions);
        context.setAttribute(Attributes.SORT_TYPE, sortOptions);


    }

    private List<SortOption> getSortOptions() {
        return Arrays.asList(new SortOption(Constants.SORT_BY_NAME_DESC, Parameters.NAME, SortOrder.DESC),
                new SortOption(Constants.SORT_BY_NAME_ASC, Parameters.NAME, SortOrder.ASC),
                new SortOption(Constants.SORT_BY_PRICE_DESC, Parameters.PRICE, SortOrder.DESC),
                new SortOption(Constants.SORT_BY_PRICE_ASC, Parameters.PRICE, SortOrder.ASC));
    }

    private List<Integer> getPageSizes() {
        return Arrays.asList(5, 10, 15);
    }

    private Map<String, CaptchaProviderStrategy> getCaptchaProviders() {
        Map<String, CaptchaProviderStrategy> captchaProviderMap = new HashMap<>();
        captchaProviderMap.put("cookie", new CaptchaProviderCookieStrategyImpl());
        captchaProviderMap.put("hiddenField", new CaptchaProviderHiddenFieldStrategyImpl());
        captchaProviderMap.put("session", new CaptchaProviderSessionStrategyImpl());
        return captchaProviderMap;
    }
}
