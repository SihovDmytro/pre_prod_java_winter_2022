package com.webShop.filter;

import com.webShop.entity.ResponseWrapper;
import com.webShop.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CompressionFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(CompressionFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (isGzipSupported(httpRequest)) {
            LOG.info("CompressionFilter started: " + httpRequest.getRequestURL());
            ResponseWrapper responseWrapper = new ResponseWrapper(httpResponse);
            chain.doFilter(httpRequest, responseWrapper);
            try {
                responseWrapper.finishResponse();
            } catch (IOException exception) {
                LOG.error("Cannot compress content");
            }
        } else {
            chain.doFilter(httpRequest, httpResponse);
        }
    }

    private boolean isGzipSupported(HttpServletRequest request) {
        String encodings = request.getHeader(Constants.ACCEPT_ENCODING_HEADER);
        return encodings != null && encodings.contains(Constants.GZIP);
    }
}