package com.task5.subtask2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileNameFilter extends Filter {
    private static final Logger LOG = LogManager.getLogger(FileNameFilter.class);
    private String name;

    public FileNameFilter(String name) {
        this.name = name;
    }

    @Override
    public boolean doFilter(File file) {
        boolean result = file.getName().toLowerCase().contains(name.toLowerCase());
        if (!result) LOG.trace("FileNameFilter fail: " + file.getAbsolutePath());
        return result;
    }
}