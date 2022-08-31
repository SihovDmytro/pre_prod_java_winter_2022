package com.webShop.servlet;

import com.webShop.entity.Order;
import com.webShop.entity.OrderStatus;
import com.webShop.entity.Product;
import com.webShop.entity.ProductInfo;
import com.webShop.entity.User;
import com.webShop.service.CartService;
import com.webShop.service.OrdersService;
import com.webShop.util.Attributes;
import com.webShop.util.Constants;
import com.webShop.util.Messages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@WebServlet("/" + Constants.BUY_CART_SERVLET)
public class BuyCartServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(BuyCartServlet.class);
    private OrdersService ordersService;

    @Override
    public void init() throws ServletException {
        ordersService = (OrdersService) getServletContext().getAttribute(Attributes.ORDERS_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("doGet start");
        User user = (User) req.getSession().getAttribute(Attributes.CURRENT_USER);

        String forward = Constants.LOGIN_PAGE_PATH;
        if (user != null) {
            forward = Constants.BUY_CART_PAGE_PATH;
        }

        LOG.debug("forward: " + forward);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.trace("doPost start");
        User user = (User) req.getSession().getAttribute(Attributes.CURRENT_USER);
        CartService cartService = (CartService) req.getSession().getAttribute(Attributes.CART);

        if (user == null) {
            resp.sendRedirect(Constants.LOGIN_PAGE_PATH);
        }
        if (cartService.getCart().isEmpty()) {
            resp.sendRedirect(Constants.PRODUCTS_PAGE_PATH);
        }

        Order order = getOrder(cartService, user);
        LOG.debug("order: " + order);
        ordersService.add(order);
        cartService.clear();

        resp.sendRedirect(Constants.RESULT_PAGE_PATH);
    }

    private Order getOrder(CartService cartService, User user) {
        Order order = new Order();
        order.setStatus(OrderStatus.ACCEPTED);
        order.setStatusDescription(Messages.ORDER_ACCEPTED);
        order.setDate(Calendar.getInstance());
        order.setUser(user);
        List<ProductInfo> products = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : cartService.getCart().entrySet()) {
            Product product = entry.getKey();
            products.add(new ProductInfo(product, entry.getValue(), product.getPrice()));
        }

        order.setProducts(products);
        return order;
    }
}
