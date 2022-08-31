package com.webShop.servlet;

import com.webShop.entity.Product;
import com.webShop.service.CartService;
import com.webShop.service.ProductsService;
import com.webShop.util.Attributes;
import com.webShop.util.Parameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private CartService cartService;
    private PrintWriter printWriter;
    private CartServlet cartServlet;
    private ProductsService productsService;
    private ServletContext servletContext;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        cartService = mock(CartService.class);
        session = mock(HttpSession.class);
        printWriter = mock(PrintWriter.class);
        productsService = mock(ProductsService.class);
        cartServlet = mock(CartServlet.class);
        servletContext = mock(ServletContext.class);
    }

    @Test
    public void shouldAddProductToCart() throws IOException, ServletException {
        Product product = new Product(
                1, "bnm",
                new BigDecimal("321"), "cat1",
                "prod1", "desk1",
                "img1");
        when(cartServlet.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(Attributes.PRODUCTS_SERVICE)).thenReturn(productsService);
        when(request.getParameter(Parameters.PRODUCT_ID)).thenReturn("1");
        when(productsService.getProduct(1)).thenReturn(Optional.of(product));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.CART)).thenReturn(cartService);
        when(cartService.count()).thenReturn(1);
        when(response.getWriter()).thenReturn(printWriter);

        doCallRealMethod().when(cartServlet).init();
        doCallRealMethod().when(cartServlet).doPost(request, response);

        cartServlet.init();
        cartServlet.doPost(request, response);

        verify(cartService, times(1)).add(product);
        verify(printWriter,times(1)).print("{\"count\":1}");
    }

    @Test
    public void shouldDeleteProductFromCart() throws IOException, ServletException {
        Product product = new Product(1, "bnm",
                new BigDecimal("321"), "cat1",
                "prod1", "desk1",
                "img1");
        when(cartServlet.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(Attributes.PRODUCTS_SERVICE)).thenReturn(productsService);
        when(request.getParameter(Parameters.PRODUCT_ID)).thenReturn("1");
        when(productsService.getProduct(1)).thenReturn(Optional.of(product));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.CART)).thenReturn(cartService);
        when(cartService.calculateTotal()).thenReturn(new BigDecimal("321"));
        when(response.getWriter()).thenReturn(printWriter);

        doCallRealMethod().when(cartServlet).init();
        doCallRealMethod().when(cartServlet).doDelete(request, response);

        cartServlet.init();
        cartServlet.doDelete(request, response);

        verify(cartService, times(1)).remove(product);
        verify(printWriter,times(1)).print("{\"cartPrice\":\"321,00\"}");
    }

    @Test
    public void shouldChangeProductNumber() throws IOException, ServletException {
        Product product = new Product(1, "bnm",
                new BigDecimal("321"), "cat1",
                "prod1", "desk1",
                "img1");
        when(cartServlet.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(Attributes.PRODUCTS_SERVICE)).thenReturn(productsService);
        when(request.getParameter(Parameters.PRODUCT_ID)).thenReturn("1");
        when(productsService.getProduct(1)).thenReturn(Optional.of(product));
        when(request.getParameter(Parameters.COUNT)).thenReturn("2");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.CART)).thenReturn(cartService);
        when(cartService.calculateTotal()).thenReturn(new BigDecimal("321"));
        when(response.getWriter()).thenReturn(printWriter);

        doCallRealMethod().when(cartServlet).init();
        doCallRealMethod().when(cartServlet).doPut(request, response);

        cartServlet.init();
        cartServlet.doPut(request, response);

        verify(cartService, times(1)).changeCount(product, 2);
        verify(printWriter,times(1)).print("{\"total\":\"642,00\",\"cartPrice\":\"321,00\"}");
    }

}