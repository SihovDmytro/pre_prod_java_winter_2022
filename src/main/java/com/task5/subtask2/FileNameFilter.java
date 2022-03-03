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
    public void doFilter(File folder) {
        LOG.trace("FileNameFilter start");
        LOG.trace("files before: " + files.size());
        files.removeIf(file -> !file.getName().toLowerCase().contains(name.toLowerCase()));
        LOG.trace("files after: " + files.size());
        LOG.trace("FileNameFilter end");
    }
}
