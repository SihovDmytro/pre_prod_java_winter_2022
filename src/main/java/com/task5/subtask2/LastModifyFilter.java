package com.task5.subtask2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Calendar;

public class LastModifyFilter extends Filter {
    private static final Logger LOG = LogManager.getLogger(LastModifyFilter.class);
    private long start;
    private long end;

    public LastModifyFilter(Calendar start, Calendar end) {
        this.start = start.getTimeInMillis();
        this.end = end.getTimeInMillis();
    }

    @Override
    public void doFilter(File folder) {
        LOG.trace("LastModifyFilter start");
        LOG.trace("files before: " + files.size());
        files.removeIf(file -> file.lastModified() < start || file.lastModified() > end);
        LOG.trace("files after: " + files.size());
        LOG.trace("LastModifyFilter end");
    }
}
