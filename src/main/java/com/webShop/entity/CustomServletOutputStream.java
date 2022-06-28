package com.webShop.entity;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomServletOutputStream extends ServletOutputStream {
    final AtomicBoolean open = new AtomicBoolean(true);

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public CustomServletOutputStream() throws IOException {
        super();
    }


    @Override
    public void write(byte[] b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        outputStream.write(b, off, len);

    }

    @Override
    public void write(int b) throws IOException {

        outputStream.write(b);
    }

    @Override
    public void close() throws IOException {
        if (open.compareAndSet(true, false)) {
            outputStream.close();
        }
    }

    @Override
    public boolean isReady() {
        return open.get();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
    }

    @Override
    public String toString() {
        return outputStream.toString();
    }
}
