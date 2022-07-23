package com.webShop.filter;

import com.webShop.entity.ResponseWrapper;
import com.webShop.util.Constants;
import com.webShop.util.ListenersUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPOutputStream;

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
            responseWrapper.close();
            LOG.info("contentType: " + httpResponse.getContentType());
            if (ListenersUtil.isContentTypeHtml(httpResponse)) {
                LOG.trace("compress content");
                httpResponse.setHeader(Constants.CONTENT_ENCODING_HEADER, Constants.GZIP);
                try (OutputStreamWriter tempOut = new OutputStreamWriter(new GZIPOutputStream(httpResponse.getOutputStream()))) {
                    tempOut.write(responseWrapper.toString());
                } catch (IOException exception) {
                    LOG.error("Cannot send response", exception);
                }
            }
        }
        chain.doFilter(httpRequest, httpResponse);

    }

    private boolean isGzipSupported(HttpServletRequest request) {
        String encodings = request.getHeader(Constants.ACCEPT_ENCODING_HEADER);
        return encodings != null && encodings.contains(Constants.GZIP);
    }
}