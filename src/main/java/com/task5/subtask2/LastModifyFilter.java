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
    public boolean doFilter(File file) {
        boolean result = file.lastModified() >= start && file.lastModified() <= end;
        if (!result) LOG.trace("LastModifyFilter fail: " + file.getAbsolutePath());
        return result;
    }
}
