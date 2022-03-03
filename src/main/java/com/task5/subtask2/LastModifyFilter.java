package com.task5.subtask2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class LastModifyFilter extends Filter{
    private static final Logger LOG = LogManager.getLogger(LastModifyFilter.class);
    @Override
    public void doFilter(File folder) {
        LOG.trace("LastModifyFilter start");
        LOG.trace("LastModifyFilter end");
    }
}
