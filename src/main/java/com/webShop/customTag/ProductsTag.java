package com.webShop.customTag;

import com.webShop.entity.Product;
import com.webShop.entity.ProductsPageBean;
import com.webShop.service.CartService;
import com.webShop.util.Attributes;
import com.webShop.util.Messages;
import com.webShop.util.Parameters;
import com.webShop.util.ProductsPageConfig;
import com.webShop.util.WebShopUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProductsTag extends TagSupport {
    private static final Logger LOG = LogManager.getLogger(ProductsTag.class);
    private static final String START_ROW = "<tr>";
    private static final String END_ROW = "</tr>";
    private static final String START_DIV = "<div class=\"pages\">";
    private static final String END_DIV = "</div>";
    private static final String START_TABLE = "<table class=\"products\">";
    private static final String END_TABLE = "</table>";
    private static final String PRODUCT_INFO = "<td>\n" +
            "                                <p><img src=\"images/%s\" width=\"200\" height=\"200\"></p>\n" +
            "                                <hr>\n" +
            "                                <p>%s</p>\n" +
            "                                <p>%s</p>\n" +
            "                                <p>Price: %.2f UAN</p>\n" +
            "                                <hr>\n" +
            "                                <button onclick=\"addToCart(%d)\">Add to cart</button>\n" +
            "                            </td>";
    private static final String DID_NOT_MATCH = "<td>\n" +
            "                                <p>%s</p>\n" +
            "                            </td>";

    private static final String REFERENCE = "<a href=%s>%d </a>";
    private static final int RANGE = 4;
    private static final int FIRST_PAGE = 1;
    private static final String PAGE_NUMBER_FORMAT = "%d ";
    private static final String QUESTION_MARK = "?";
    private static final String CART = "<div>\n" +
            "                <a href=\"cart.jsp\"><img id=\"cart-img\" src=\"images/icons/cart.png\" width=\"64\" height=\"64\"></a>\n" +
            "                <label id=\"cart-size\" for=\"cart-img\">%d</label>\n" +
            "            </div>";

    @Override
    public int doStartTag() {
        try {
            LOG.trace("ProductsTag start");
            JspWriter writer = pageContext.getOut();
            List<Product> products = (List<Product>) pageContext.getRequest().getAttribute(Attributes.PRODUCTS);
            if (!products.isEmpty()) {
                writePages(writer);

                writer.write(START_TABLE);

                int count = 0;
                for (Product product : products) {
                    if (count == 0) {
                        writer.write(START_ROW);
                    }

                    writer.write(String.format(PRODUCT_INFO, product.getImage(), product.getName(), product.getDescription(), product.getPrice(), product.getId()));
                    count++;

                    if (count >= ProductsPageConfig.PRODUCTS_PER_LINE) {
                        writer.write(END_ROW);
                        count = 0;
                    }
                }

                writer.write(END_TABLE);
            } else {
                writer.write(String.format(DID_NOT_MATCH, Messages.DID_NOT_MATCH));
            }
        } catch (IOException exception) {
            LOG.error("Cannot print products", exception);
        }
        return SKIP_BODY;
    }

    private void writePages(JspWriter writer) throws IOException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        ProductsPageBean bean = (ProductsPageBean) request.getSession().getAttribute(Attributes.PRODUCTS_PAGE_BEAN);
        int countProducts = (int) request.getAttribute(Attributes.COUNT_PRODUCTS);
        LOG.debug("countProducts: " + countProducts);
        int pageSize = bean.getPageSize();
        LOG.debug("pageSize: " + pageSize);

        if (pageSize < countProducts) {
            writer.write(START_DIV);

            String url = (String) request.getAttribute(Attributes.URL);
            LOG.debug("url: " + url);
            String params = request.getQueryString();
            LOG.trace("params: " + params);
            int page = bean.getPage();

            if (page > FIRST_PAGE) {
                writer.write(String.format(REFERENCE, url + QUESTION_MARK + updatePage(params, FIRST_PAGE), FIRST_PAGE));
                for (int i = page - RANGE; i < page; i++) {
                    if (i > FIRST_PAGE) {
                        writer.write(String.format(REFERENCE, url + QUESTION_MARK + updatePage(params, i), i));
                    }
                }
            }

            writer.write(String.format(PAGE_NUMBER_FORMAT, page));

            int lastPage = (int) Math.ceil((double) countProducts / pageSize);
            if (page < lastPage) {
                for (int i = page + 1; i < page + RANGE; i++) {
                    if (i < lastPage) {
                        writer.write(String.format(REFERENCE, url + QUESTION_MARK + updatePage(params, i), i));
                    }
                }
                writer.write(String.format(REFERENCE, url + QUESTION_MARK + updatePage(params, lastPage), lastPage));
            }

            writer.write(END_DIV);
        }
        CartService cartService = (CartService) pageContext.getSession().getAttribute(Attributes.CART);
        writer.write(String.format(CART, cartService.count()));
    }

    private String updatePage(String params, int page) {
        LOG.debug("before: " + params);
        Map<String, List<String>> paramsMap = WebShopUtil.extractParams(params);
        paramsMap.put(Parameters.PAGE, Arrays.asList(String.valueOf(page)));
        LOG.debug("after: " + WebShopUtil.paramsToString(paramsMap));
        return WebShopUtil.paramsToString(paramsMap);
    }
}
