package com.webShop.filter;

import com.webShop.entity.ResponseWrapper;
import com.webShop.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPOutputStream;

public class CompressionFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(CompressionFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG.trace("CompressionFilter start");
        chain.doFilter(request, response);
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (!isGzipSupported(httpRequest) || contentTypeIsText(httpRequest)) {
            chain.doFilter(request, response);
        } else {
            LOG.trace("Response compressing");
            httpResponse.setHeader(Constants.CONTENT_ENCODING_HEADER, Constants.GZIP);
            ResponseWrapper responseWrapper = new ResponseWrapper(httpResponse);
            chain.doFilter(request, responseWrapper);
            responseWrapper.close();
            try (OutputStreamWriter tempOut = new OutputStreamWriter(new GZIPOutputStream(httpResponse.getOutputStream()))) {
                LOG.debug("response: " + responseWrapper);
                tempOut.write(responseWrapper.toString());
            } catch (IOException exception) {
                LOG.error("Cannot send response", exception);
            }
        }

    }

    private boolean isGzipSupported(HttpServletRequest request) {
        String encodings = request.getHeader(Constants.ACCEPT_ENCODING_HEADER);
        return encodings != null && encodings.contains(Constants.GZIP);
    }

    private boolean contentTypeIsText(HttpServletRequest request) {
        String contentType = request.getHeader(Constants.CONTENT_TYPE_HEADER);
        return contentType != null && contentType.startsWith(Constants.TEXT);
    }
}