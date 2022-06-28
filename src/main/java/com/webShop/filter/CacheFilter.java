package com.webShop.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class CacheFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(CacheFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG.trace("CacheFilter start");
        chain.doFilter(request, response);
    }
}