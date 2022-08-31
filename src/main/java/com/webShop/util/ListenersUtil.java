package com.webShop.util;

import javax.servlet.http.HttpServletResponse;

public class ListenersUtil {
    public static boolean isContentTypeHtml(HttpServletResponse response) {
        String contentType = response.getContentType();
        return contentType != null && contentType.startsWith(Constants.TEXT_HTML);
    }
}
