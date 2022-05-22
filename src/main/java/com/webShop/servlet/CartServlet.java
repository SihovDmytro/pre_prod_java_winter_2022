package com.webShop.servlet;

import com.webShop.entity.Product;
import com.webShop.service.CartService;
import com.webShop.service.ProductsService;
import com.webShop.util.Attributes;
import com.webShop.util.Constants;
import com.webShop.util.Parameters;
import com.webShop.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Optional;

@WebServlet("/" + Constants.CART_SERVLET)
public class CartServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(CartServlet.class);
    private ProductsService productsService;

    @Override
    public void init() throws ServletException {
        productsService = (ProductsService) getServletContext().getAttribute(Attributes.PRODUCTS_SERVICE);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        LOG.trace("doDelete start");

        processRequest(req, resp, (product -> {
            CartService cartService = getCartService(req);
            cartService.remove(product);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.CART_PRICE, String.format(Constants.PRICE_FORMAT, cartService.calculateTotal()));
            String jsonString = jsonObject.toJSONString();
            LOG.debug("jsonString: " + jsonString);
            resp.setContentType(Constants.JSON_TYPE);

            PrintWriter printWriter = resp.getWriter();
            printWriter.print(jsonString);
            printWriter.flush();
        }));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        LOG.trace("doPut start");

        processRequest(req, resp, (product -> {
            CartService cartService = getCartService(req);
            int count = getCount(req);
            if (!Validator.validateCount(count)) {
                throw new ServletException("Invalid count");
            }

            cartService.changeCount(product, count);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.TOTAL, String.format(Constants.PRICE_FORMAT, product.getPrice().multiply(new BigDecimal(count))));
            jsonObject.put(Constants.CART_PRICE, String.format(Constants.PRICE_FORMAT, cartService.calculateTotal()));
            String jsonString = jsonObject.toJSONString();
            LOG.debug("jsonString: " + jsonString);
            resp.setContentType(Constants.JSON_TYPE);

            PrintWriter printWriter = resp.getWriter();
            printWriter.print(jsonString);
            printWriter.flush();
        }));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        LOG.trace("doPost start");

        processRequest(req, resp, (product) -> {
            CartService cartService = getCartService(req);
            cartService.add(product);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.COUNT, cartService.count());
            String jsonString = jsonObject.toJSONString();
            LOG.debug("jsonString: " + jsonString);
            resp.setContentType(Constants.JSON_TYPE);

            PrintWriter printWriter = resp.getWriter();
            printWriter.print(jsonString);
            printWriter.flush();
        });
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, Operation operation) {
        try {
            Optional<Product> optionalProduct = productsService.getProduct(getProductID(request));

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                LOG.debug("product: " + product);
                operation.execute(product);
            } else {
                LOG.error("Cannot find product");
                throw new ServletException();
            }
        } catch (NumberFormatException | IOException | ServletException exception) {
            LOG.error("Cannot update product", exception);
            response.setStatus(Constants.STATUS_FAIL);
        }
    }

    private int getProductID(HttpServletRequest request) throws NumberFormatException {
        return Integer.parseInt(request.getParameter(Parameters.PRODUCT_ID));
    }

    private int getCount(HttpServletRequest request) throws NumberFormatException {
        return Integer.parseInt(request.getParameter(Parameters.COUNT));
    }

    private CartService getCartService(HttpServletRequest request) {
        return (CartService) request.getSession().getAttribute(Attributes.CART);
    }
}
