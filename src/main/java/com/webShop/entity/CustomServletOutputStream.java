package com.webShop.entity;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class CustomServletOutputStream extends ServletOutputStream {
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String GZIP = "gzip";

    protected ByteArrayOutputStream byteArrayOutputStream;
    protected GZIPOutputStream gzipstream;
    protected boolean closed;
    protected HttpServletResponse response;
    protected ServletOutputStream output;

    public CustomServletOutputStream(HttpServletResponse response) throws IOException {
        closed = false;
        this.response = response;
        output = response.getOutputStream();
        byteArrayOutputStream = new ByteArrayOutputStream();
        gzipstream = new GZIPOutputStream(byteArrayOutputStream);
    }

    public void close() throws IOException {
        if (closed) {
            throw new IOException("This output stream has already been closed");
        }
        gzipstream.finish();

        byte[] bytes = byteArrayOutputStream.toByteArray();

        response.addHeader(CONTENT_ENCODING, GZIP);
        output.write(bytes);
        output.flush();
        output.close();
        closed = true;
    }

    public void flush() throws IOException {
        if (closed) {
            throw new IOException("Cannot flush a closed output stream");
        }
        gzipstream.flush();
    }

    public void write(int b) throws IOException {
        if (closed) {
            throw new IOException("Cannot write to a closed output stream");
        }
        gzipstream.write((byte)b);
    }

    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len) throws IOException {
        if (closed) {
            throw new IOException("Cannot write to a closed output stream");
        }
        gzipstream.write(b, off, len);
    }

    public boolean closed() {
        return (this.closed);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}
