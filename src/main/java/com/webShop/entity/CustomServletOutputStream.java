package com.webShop.entity;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class CustomServletOutputStream extends ServletOutputStream {
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String GZIP = "gzip";
    public static final String CONTENT_LENGTH = "Content-Length";

    protected ByteArrayOutputStream byteArrayOutputStream;
    protected OutputStream gzipstream;
    protected boolean closed;
    protected HttpServletResponse response;
    protected ServletOutputStream servletOutput;
    boolean useCompression;

    public CustomServletOutputStream(HttpServletResponse response, boolean useCompression) throws IOException {
        closed = false;
        this.useCompression = useCompression;
        this.response = response;
        servletOutput = response.getOutputStream();
        byteArrayOutputStream = new ByteArrayOutputStream();
        if (useCompression) {
            gzipstream = new GZIPOutputStream(byteArrayOutputStream);
        } else {
            gzipstream = byteArrayOutputStream;
        }

    }

    public void close() throws IOException {
        if (closed) {
            throw new IOException("This output stream has already been closed");
        }
        if (useCompression) {
            response.addHeader(CONTENT_ENCODING, GZIP);
            ((GZIPOutputStream) gzipstream).finish();
        }

        byte[] bytes = byteArrayOutputStream.toByteArray();
        response.addHeader(CONTENT_LENGTH,
                Integer.toString(bytes.length));
        servletOutput.write(bytes);
        servletOutput.flush();
        servletOutput.close();
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
        gzipstream.write((byte) b);
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) throws IOException {
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
