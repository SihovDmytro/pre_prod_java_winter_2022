package com.webShop.servlet;

import com.webShop.entity.Order;
import com.webShop.entity.Product;
import com.webShop.entity.User;
import com.webShop.service.CartService;
import com.webShop.service.OrdersService;
import com.webShop.util.Attributes;
import com.webShop.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BuyCartServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private CartService cartService;
    private BuyCartServlet buyCartServlet;
    private OrdersService ordersService;
    private ServletContext servletContext;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        cartService = mock(CartService.class);
        session = mock(HttpSession.class);
        servletContext = mock(ServletContext.class);
        buyCartServlet = mock(BuyCartServlet.class);
        ordersService = mock(OrdersService.class);
    }

    @Test
    public void shouldMakeOrder() throws ServletException, IOException {
        User user = new User("dmytro", "Dmytro", "Sihov",
                "abrakadabra", "Dmytro_Sihov@epam.com", false);
        Map<Product, Integer> cart = new HashMap<>();
        Product product = new Product(1, "bnm",
                new BigDecimal("321"), "cat1",
                "prod1", "desk1",
                "img1");
        cart.put(product, 2);

        when(buyCartServlet.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(Attributes.ORDERS_SERVICE)).thenReturn(ordersService);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.CURRENT_USER)).thenReturn(user);
        when(session.getAttribute(Attributes.CART)).thenReturn(cartService);
        when(cartService.getCart()).thenReturn(cart);


        doCallRealMethod().when(buyCartServlet).init();
        doCallRealMethod().when(buyCartServlet).doPost(request, response);

        buyCartServlet.init();
        buyCartServlet.doPost(request, response);

        verify(ordersService, times(1)).add(any(Order.class));
        verify(cartService, times(1)).clear();
    }

    @Test
    public void shouldRedirectToLoginPage() throws ServletException, IOException {
        when(buyCartServlet.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(Attributes.ORDERS_SERVICE)).thenReturn(ordersService);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.CURRENT_USER)).thenReturn(null);
        when(session.getAttribute(Attributes.CART)).thenReturn(cartService);

        doCallRealMethod().when(buyCartServlet).init();
        doCallRealMethod().when(buyCartServlet).doPost(request, response);

        buyCartServlet.init();
        buyCartServlet.doPost(request, response);

        verify(response, times(1)).sendRedirect(Constants.LOGIN_PAGE_PATH);
    }

}