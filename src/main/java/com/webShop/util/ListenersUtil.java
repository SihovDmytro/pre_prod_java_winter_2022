package com.webShop.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListenersUtil {
    public static boolean isContentTypeHTML(HttpServletResponse response) {
        return Constants.TEXT_HTML.equals(response.getContentType());
    }

    public static boolean acceptsHTML(HttpServletRequest request) {
        String accept = request.getHeader(Constants.ACCEPT_HEADER);
        return accept != null && accept.contains(Constants.TEXT_HTML);
    }
}
