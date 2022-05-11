package com.webShop.servlet;

import com.webShop.entity.Product;
import com.webShop.entity.ProductsPageBean;
import com.webShop.entity.SortOption;
import com.webShop.service.ProductsService;
import com.webShop.util.Attributes;
import com.webShop.util.Constants;
import com.webShop.util.Parameters;
import com.webShop.util.ProductsPageConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@WebServlet("/" + Constants.FILTER_PRODUCTS_SERVLET)
public class FilterProductsServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(FilterProductsServlet.class);
    private ProductsService productsService;

    @Override
    public void init() throws ServletException {
        productsService = (ProductsService) getServletContext().getAttribute(Attributes.PRODUCTS_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<SortOption> sortOptions = (List<SortOption>) getServletContext().getAttribute(Attributes.SORT_TYPE);
        LOG.debug("sortOptions: " + sortOptions);

        ProductsPageBean productsPageBean = readBean(req, sortOptions);
        if (!productsPageBean.equals(req.getSession().getAttribute(Attributes.PRODUCTS_PAGE_BEAN))) {
            productsPageBean.setPage(ProductsPageConfig.DEFAULT_PAGE);
        }
        LOG.debug("productsPageBean: " + productsPageBean);
        req.getSession().setAttribute(Attributes.PRODUCTS_PAGE_BEAN, productsPageBean);

        List<Product> products = productsService.getProducts(productsPageBean);
        LOG.trace("products: " + Arrays.toString(products.toArray()));
        req.setAttribute(Attributes.PRODUCTS, products);

        int countProducts = productsService.countProducts(productsPageBean);
        LOG.debug("countProducts: " + countProducts);
        req.setAttribute(Attributes.COUNT_PRODUCTS, countProducts);

        String url = req.getRequestURI();
        LOG.trace("url: " + url);
        req.setAttribute(Attributes.URL, url);

        req.getRequestDispatcher(Constants.PRODUCTS_PAGE_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private ProductsPageBean readBean(HttpServletRequest request, List<SortOption> sortOptions) {
        BigDecimal minPrice = null;
        try {
            minPrice = new BigDecimal(request.getParameter(Parameters.MIN_PRICE));
        } catch (NumberFormatException | NullPointerException exception) {
            LOG.error("Cannot parse minPrice");
        }

        BigDecimal maxPrice = null;
        try {
            maxPrice = new BigDecimal(request.getParameter(Parameters.MAX_PRICE));
        } catch (NumberFormatException | NullPointerException exception) {
            LOG.error("Cannot parse maxPrice");
        }

        String name = request.getParameter(Parameters.NAME);
        String category = request.getParameter(Parameters.CATEGORY);
        String[] producers = request.getParameterValues(Parameters.PRODUCERS);

        int pageSize = ProductsPageConfig.DEFAULT_PAGE_SIZE;
        try {
            pageSize = Integer.parseInt(request.getParameter(Parameters.PAGE_SIZE));
        } catch (NumberFormatException exception) {
            LOG.error("Cannot parse pageSize");
        }

        int page = ProductsPageConfig.DEFAULT_PAGE;
        try {
            page = Integer.parseInt(request.getParameter(Parameters.PAGE));
        } catch (NumberFormatException exception) {
            LOG.error("Cannot parse page");
        }

        SortOption sortType = sortOptions.stream()
                .filter(sortOption -> sortOption.getSortName().equals(request.getParameter(Parameters.SORT_TYPE)))
                .findFirst()
                .orElse(ProductsPageConfig.DEFAULT_SORT_OPTION);
        return new ProductsPageBean(minPrice, maxPrice, name, category, producers, pageSize, page, sortType);
    }

}
