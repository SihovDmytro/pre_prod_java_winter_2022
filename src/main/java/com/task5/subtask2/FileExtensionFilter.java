package com.task5.subtask2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileExtensionFilter extends Filter {
    private static final Logger LOG = LogManager.getLogger(FileExtensionFilter.class);
    private String extension;

    FileExtensionFilter(String extension) {
        this.extension = extension;
    }

    @Override
    public void doFilter(File folder) {
        LOG.trace("FileExtensionFilter start");
        LOG.trace("files before: " + files.size());
        files.removeIf(file -> !file.getName().endsWith(extension));
        LOG.trace("files before: " + files.size());
        LOG.trace("FileExtensionFilter end");
    }

}
