package com.webShop.customTag;

import com.webShop.entity.Product;
import com.webShop.service.CartService;
import com.webShop.util.Attributes;
import com.webShop.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class CartTag extends TagSupport {
    private static final Logger LOG = LogManager.getLogger(CartTag.class);
    private static final String START_ROW = "<tr>";
    private static final String END_ROW = "</tr>";
    private static final String START_TABLE = "<table id=\"cart\">";
    private static final String END_TABLE = "</table>";
    public static final String THEAD = "<thead>\n" +
            "        <tr>\n" +
            "            <th></th>\n" +
            "            <th></th>\n" +
            "            <th>Number</th>\n" +
            "            <th>Price, UAN</th>\n" +
            "            <th>Total, UAN</th>\n" +
            "            <th></th>\n" +
            "        </tr>\n" +
            "        </thead>";
    private static final String PRODUCT_INFO = "<td><img src=\"images/%s\" width=\"100\" height=\"100\"></td>\n" +
            "                           <td>%s</td>\n" +
            "                           <td><input type=\"number\" id=\"countProducts\" onchange=\"updateCart(%d, this)\" min=\"1\" max=\"100\" step=\"1\" value=%d /></td>\n" +
            "                           <td>%.2f</td>\n" +
            "                           <td>%.2f</td>\n" +
            "                           <td><img src=\"images/icons/trash.png\" width=\"32\" height=\"32\" onclick=\"deleteFromCart(%d, this)\"/></td>\n";
    private static final String CART_PRICE = "<tr>" +
            "<td>Cart price: </td>" +
            "<td> %.2f</td>" +
            "</tr>";
    private static final String BUY_CART = "<a href=\"%s\">Proceed</a>";

    @Override
    public int doStartTag() {
        try {
            LOG.trace("CartTag start");
            JspWriter writer = pageContext.getOut();
            CartService cartService = (CartService) pageContext.getSession().getAttribute(Attributes.CART);
            Map<Product, Integer> cart = cartService.getCart();

            writer.write(START_TABLE);
            writer.write(THEAD);
            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                writer.write(START_ROW);
                Product product = entry.getKey();
                int count = entry.getValue();

                writer.write(String.format(PRODUCT_INFO,
                        product.getImage(),
                        product.getName(),
                        product.getId(),
                        count,
                        product.getPrice(),
                        product.getPrice().multiply(new BigDecimal(count)),
                        product.getId()));

                writer.write(END_ROW);
            }

            writer.write(String.format(CART_PRICE, cartService.calculateTotal()));
            writer.write(END_TABLE);
            if (!cart.isEmpty()) {
                writer.write(String.format(BUY_CART, Constants.BUY_CART_SERVLET));
            }
        } catch (IOException exception) {
            LOG.error("Cannot print cart", exception);
        }
        return SKIP_BODY;
    }
}
