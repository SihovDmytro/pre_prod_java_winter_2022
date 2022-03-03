package com.task5.subtask2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileSizeFilter extends Filter {
    private static final Logger LOG = LogManager.getLogger(FileSizeFilter.class);

    @Override
    public void doFilter(File folder) {
        LOG.trace("FileSizeFilter start");
        LOG.trace("FileSizeFilter end");
    }
}
