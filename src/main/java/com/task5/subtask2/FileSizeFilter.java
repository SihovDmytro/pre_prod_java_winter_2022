package com.task5.subtask2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileSizeFilter extends Filter {
    private static final Logger LOG = LogManager.getLogger(FileSizeFilter.class);
    private long minSize;
    private long maxSize;

    public FileSizeFilter(long min, long max) {
        minSize = min;
        maxSize = max;
    }

    @Override
    public void doFilter(File folder) {
        LOG.trace("FileSizeFilter start");
        LOG.trace("files before: " + files.size());
        files.removeIf(file -> file.length() < minSize || file.length() > maxSize);
        LOG.trace("files after: " + files.size());
        LOG.trace("FileSizeFilter end");
    }
}
