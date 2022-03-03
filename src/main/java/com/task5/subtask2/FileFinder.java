package com.task5.subtask2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FileFinder {
    private static final Logger LOG = LogManager.getLogger(FileFinder.class);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LOG.trace("Start application");
        boolean continueWork = true;
        while (continueWork) {
            File folder = enterFolder();
            if (folder == null) {
                System.out.println("This folder does not exist");
                break;
            }
            LOG.trace("folder: " + folder.getAbsolutePath());
            Filter filter = getFilter();
            if (filter != null) {
                filter.filterManager(folder);
                List<File> foundFiles = filter.getFiles();
                if (foundFiles != null) {
                    LOG.trace("foundFiles size :" + foundFiles.size());
                    printFoundFiles(foundFiles);
                }
            }
            System.out.println("Search files in another folder?(1/0)");
            if (!scanner.nextLine().equals("1")) {
                LOG.trace("End");
                continueWork = false;
            }
        }
        scanner.close();


    }

    private static void printFoundFiles(List<File> files) {
        System.out.println("Found files: ");
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }

    private static File enterFolder() {
        System.out.println("Enter path to folder: ");
        String path = scanner.nextLine();
        LOG.debug("path: " + path);
        if (path != null) {
            File folder = new File(path);
            if (folder.isDirectory() && folder.exists())
                return folder;
        }
        return null;
    }

    private static Filter getFilter() {
        List<Filter> filters = new ArrayList<>();
        Filter filter = null;
        System.out.println("Use search by file name?(1/0)");
        if (scanner.nextLine().equals("1")) {
            LOG.trace("Use filename filter");
            System.out.println("Enter name: ");
            String name = scanner.nextLine();
            LOG.trace("name: " + name);
            if (name != null)
                filters.add(new FileNameFilter(name));
            else System.out.println("Invalid name");
        }
        System.out.println("Use search by extension?(1/0)");
        if (scanner.nextLine().equals("1")) {
            LOG.trace("Use extension filter");
            System.out.println("Enter extension: ");
            String extension = scanner.nextLine();
            LOG.trace("extension: " + extension);
            if (extension.contains("."))
                filters.add(new FileExtensionFilter(extension));
            else System.out.println("Invalid extension: " + extension);
        }
        System.out.println("Use search by size?(1/0)");
        if (scanner.nextLine().equals("1")) {
            LOG.trace("Use file size filter");
            filters.add(new FileSizeFilter());
        }
        System.out.println("Use search by last modified date?(1/0)");
        if (scanner.nextLine().equals("1")) {
            LOG.trace("Use last modify filter");
            filters.add(new LastModifyFilter());
        }
        Iterator<Filter> iterator = filters.iterator();
        LOG.trace("Filters to apply: " + filters.size());
        if (iterator.hasNext()) filter = iterator.next();
        Filter tempFilter = filter;
        while (iterator.hasNext()) {
            tempFilter = tempFilter.setNextFilter(iterator.next());
        }
        return filter;
    }

}
