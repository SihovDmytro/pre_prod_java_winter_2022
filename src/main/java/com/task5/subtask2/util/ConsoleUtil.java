package com.task5.subtask2.util;

import com.task4.Util;
import com.task5.subtask2.FileExtensionFilter;
import com.task5.subtask2.FileNameFilter;
import com.task5.subtask2.FileSizeFilter;
import com.task5.subtask2.Filter;
import com.task5.subtask2.LastModifyFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class ConsoleUtil {
    private static final Logger LOG = LogManager.getLogger(ConsoleUtil.class);
    private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";

    public static void printFiles(List<File> files) {
        System.out.println("Files: ");
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
        System.out.println("Total: " + files.size());
    }

    public static File readFolderFromConsole(Scanner scanner) {
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

    public static Filter readFilterFromConsole(Scanner scanner) {
        List<Filter> filters = new ArrayList<>();
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
            try {
                System.out.println("Enter minimum file size(bytes):");
                long min = Long.parseLong(scanner.nextLine());
                LOG.trace("min file size: " + min);
                System.out.println("Enter maximum file size(bytes):");
                long max = Long.parseLong(scanner.nextLine());
                LOG.trace("max file size: " + max);
                filters.add(new FileSizeFilter(min, max));
            } catch (NumberFormatException exception) {
                LOG.debug("Cannot parse value");
                System.out.println("Invalid value");
            }
        }
        System.out.println("Use search by last modified date?(1/0)");
        if (scanner.nextLine().equals("1")) {
            LOG.trace("Use last modify filter");
            System.out.println("Enter start date(" + DATE_FORMAT + "):");
            String startDate = scanner.nextLine();
            LOG.trace("startDate: " + startDate);
            Calendar startDateCalendar = Util.stringToCalendar(startDate, new SimpleDateFormat(DATE_FORMAT));
            System.out.println("Enter end date(" + DATE_FORMAT + "):");
            String endDate = scanner.nextLine();
            LOG.trace("endDate: " + endDate);
            Calendar endDateCalendar = Util.stringToCalendar(endDate, new SimpleDateFormat(DATE_FORMAT));
            if (startDateCalendar != null && endDateCalendar != null) {
                filters.add(new LastModifyFilter(startDateCalendar, endDateCalendar));
            } else {
                System.out.println("Invalid date");
                LOG.trace("Invalid date");
            }
        }
        return FilterUtil.connectFilters(filters);
    }

}
