package com.task5.subtask2.util;

import com.task5.subtask2.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class FilterUtil {
    private static final Logger LOG = LogManager.getLogger(FileUtil.class);

    public static Filter connectFilters(List<Filter> filters)
    {
        Filter filter = null;
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
