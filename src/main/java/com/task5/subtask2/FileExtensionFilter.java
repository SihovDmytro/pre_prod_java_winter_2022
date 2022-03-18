package com.task5.subtask2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileExtensionFilter extends Filter {
    private static final Logger LOG = LogManager.getLogger(FileExtensionFilter.class);
    private String extension;

    public FileExtensionFilter(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean doFilter(File file) {
        boolean result = file.getName().endsWith(extension);
        if (!result) LOG.trace("FileExtensionFilter fail: " + file.getAbsolutePath());
        return result;
    }

}
