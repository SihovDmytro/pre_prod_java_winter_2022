package com.webShop.filter;

import com.webShop.util.ListenersUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CacheFilter implements Filter {
    private static final String CACHE_CONTROL = "Cache-control";
    private static final String NO_CACHE = "no-cache";
    private static final String PRAGMA = "Pragma";
    private static final String EXPIRES = "Expires";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader(CACHE_CONTROL, NO_CACHE);
        httpResponse.setHeader(PRAGMA, NO_CACHE);
        httpResponse.setDateHeader(EXPIRES, -1);
        chain.doFilter(httpRequest, httpResponse);
    }
}