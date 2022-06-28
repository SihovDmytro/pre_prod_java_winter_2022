package com.webShop.entity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ResponseWrapper extends HttpServletResponseWrapper {

    private ServletOutputStream outputStream;
    private PrintWriter printWriter;
    private CustomServletOutputStream customServletOutputStream;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public void close() throws IOException {
        if (printWriter != null) {
            printWriter.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
        customServletOutputStream.close();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (printWriter != null) {
            throw new IllegalStateException();
        }
        if (outputStream == null) {
            outputStream = customServletOutputStream = new CustomServletOutputStream();
        }
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStream != null) {
            throw new IllegalStateException();
        }
        if (printWriter == null) {
            customServletOutputStream = new CustomServletOutputStream();
            printWriter = new PrintWriter(new OutputStreamWriter(customServletOutputStream, getResponse().getCharacterEncoding()));
        }
        return printWriter;
    }

    @Override
    public String toString() {
        return customServletOutputStream.toString();
    }
}
