package com.webShop.util;

import javax.servlet.http.HttpServletResponse;

public class ListenersUtil {
    public static boolean isContentTypeHtml(HttpServletResponse response) {
        return response.getContentType().startsWith(Constants.TEXT_HTML);
    }
}
