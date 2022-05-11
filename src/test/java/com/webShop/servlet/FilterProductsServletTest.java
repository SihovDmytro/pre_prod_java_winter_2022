package com.webShop.servlet;

import com.webShop.entity.ProductsPageBean;
import com.webShop.entity.SortOption;
import com.webShop.entity.SortOrder;
import com.webShop.service.ProductsService;
import com.webShop.util.Attributes;
import com.webShop.util.Constants;
import com.webShop.util.Parameters;
import com.webShop.util.ProductsPageConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FilterProductsServletTest {
    private HttpServletResponse response;
    private HttpServletRequest request;
    private ServletContext context;
    private FilterProductsServlet servlet;
    private ProductsService productsService;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        context = mock(ServletContext.class);
        servlet = mock(FilterProductsServlet.class);
        productsService = mock(ProductsService.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void shouldResetThePageIfFilterWasChanged() throws ServletException, IOException {
        ProductsPageBean expected = new ProductsPageBean();
        expected.setPage(1);
        expected.setMinPrice(new BigDecimal("10"));
        expected.setSortType(ProductsPageConfig.DEFAULT_SORT_OPTION);
        expected.setPageSize(ProductsPageConfig.DEFAULT_PAGE_SIZE);

        when(context.getAttribute(Attributes.PRODUCTS_SERVICE)).thenReturn(productsService);
        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.SORT_TYPE)).thenReturn(getSortOptions());
        when(request.getParameter(Parameters.MIN_PRICE)).thenReturn("10");
        when(request.getParameter(Parameters.PAGE)).thenReturn("2");
        when(request.getSession()).thenReturn(session);

        ProductsPageBean previousBean = new ProductsPageBean();
        previousBean.setPage(2);
        previousBean.setMinPrice(new BigDecimal("0"));
        previousBean.setSortType(ProductsPageConfig.DEFAULT_SORT_OPTION);
        previousBean.setPageSize(ProductsPageConfig.DEFAULT_PAGE_SIZE);
        when(session.getAttribute(Attributes.PRODUCTS_PAGE_BEAN)).thenReturn(previousBean);
        when(productsService.getProducts(any())).thenReturn(new ArrayList<>());
        when(productsService.countProducts(any())).thenReturn(0);
        when(request.getRequestDispatcher(Constants.PRODUCTS_PAGE_PATH)).thenReturn(dispatcher);

        doCallRealMethod().when(servlet).doGet(request, response);
        doCallRealMethod().when(servlet).init();

        servlet.init();
        servlet.doGet(request, response);

        verify(session, times(1)).setAttribute(Attributes.PRODUCTS_PAGE_BEAN, expected);
    }

    private List<SortOption> getSortOptions() {
        return Arrays.asList(new SortOption(Constants.SORT_BY_NAME_DESC, Parameters.NAME, SortOrder.DESC),
                new SortOption(Constants.SORT_BY_NAME_ASC, Parameters.NAME, SortOrder.ASC),
                new SortOption(Constants.SORT_BY_PRICE_DESC, Parameters.PRICE, SortOrder.DESC),
                new SortOption(Constants.SORT_BY_PRICE_ASC, Parameters.PRICE, SortOrder.ASC));
    }
}