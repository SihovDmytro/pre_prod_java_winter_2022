package com.task5.subtask2.util;

import com.task5.subtask2.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final Logger LOG = LogManager.getLogger(FileUtil.class);

    private static void findAllFiles(File folder, List<File> files, Filter filter) {
        File[] fileArray = folder.listFiles();
        if (fileArray != null)
            for (File file : fileArray) {
                if (file.isFile()) {
                    boolean result = filter.performFiltering(file);
                    LOG.debug("file to filter: " + file.getAbsolutePath() + "\nresult: " + result);
                    if (result)
                        files.add(file);
                } else if (file.isDirectory()) {
                    findAllFiles(file, files, filter);
                }
            }
    }

    public static List<File> getAllFiles(File folder, Filter filter) {
        List<File> files = new ArrayList<>();
        FileUtil.findAllFiles(folder, files, filter);
        return files;
    }
}
