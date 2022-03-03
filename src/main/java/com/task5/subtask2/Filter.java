package com.task5.subtask2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Filter {
    private Filter nextFilter;
    protected static List<File> files;
    private static final Logger LOG = LogManager.getLogger(Filter.class);

    public List<File> getFiles() {
        return files;
    }

    public Filter setNextFilter(Filter filter) {
        nextFilter = filter;
        return nextFilter;
    }

    public abstract void doFilter(File folder);

    protected void filterManager(File folder) {
        if (files == null) files = getAllFiles(folder);
        doFilter(folder);
        if (nextFilter != null) {
            nextFilter.filterManager(folder);
        }
    }

    public static List<File> getAllFiles(File folder) {
        List<File> files = new ArrayList<>();
        findAllFiles(folder, files);
        return files;
    }

    private static void findAllFiles(File folder, List<File> files) {
        File[] fileArray = folder.listFiles();
        if (fileArray != null)
            for (File file : fileArray) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    findAllFiles(file, files);
                }
            }
    }

}
