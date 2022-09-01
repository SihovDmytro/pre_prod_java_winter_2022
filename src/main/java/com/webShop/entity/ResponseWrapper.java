package com.webShop.entity;

import com.webShop.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ResponseWrapper extends HttpServletResponseWrapper {
    protected HttpServletResponse origResponse;
    protected ServletOutputStream stream = null;
    protected PrintWriter writer = null;
    private static final Logger LOG = LogManager.getLogger(ResponseWrapper.class);

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        origResponse = response;
    }

    public ServletOutputStream createOutputStream() throws IOException {
        String contentType = origResponse.getContentType();
        LOG.info("content-type: " + contentType);
        if (contentType != null && contentType.contains(Constants.TEXT)) {
            return new CustomServletOutputStream(origResponse, true);
        }
        return new CustomServletOutputStream(origResponse, false);
    }

    public void finishResponse() throws IOException {
        if (writer != null) {
            writer.close();
        } else if (stream != null) {
            stream.close();
        }
    }

    public void flushBuffer() throws IOException {
        stream.flush();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called!");
        }

        if (stream == null)
            stream = createOutputStream();
        return stream;
    }

    public PrintWriter getWriter() throws IOException {
        if (writer != null) {
            return writer;
        }

        if (stream != null) {
            throw new IllegalStateException("getOutputStream() has already been called!");
        }

        stream = createOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8));
        return writer;
    }
}
