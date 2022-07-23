package com.webShop.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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