package com.task5.subtask2;

import com.task5.subtask2.util.ConsoleUtil;
import com.task5.subtask2.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class FileFinder {
    private static final Logger LOG = LogManager.getLogger(FileFinder.class);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LOG.trace("Start application");
        boolean continueWork = true;
        while (continueWork) {
            File folder = ConsoleUtil.readFolderFromConsole(scanner);
            if (folder == null) {
                System.out.println("This folder does not exist");
                continue;
            }
            LOG.trace("folder: " + folder.getAbsolutePath());
            Filter filter = ConsoleUtil.readFilterFromConsole(scanner);
            if (filter != null) {
                filter.performFiltering(folder);
                List<File> foundFiles = FileUtil.getAllFiles(folder, filter);
                LOG.trace("foundFiles size :" + foundFiles.size());
                ConsoleUtil.printFiles(foundFiles);
            }
            System.out.println("Search files in another folder?(1/0)");
            if (!scanner.nextLine().equals("1")) {
                LOG.trace("End");
                continueWork = false;
            }
        }
        scanner.close();
    }
}
