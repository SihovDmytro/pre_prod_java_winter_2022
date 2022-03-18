package com.task5.subtask2;

import java.io.File;

public abstract class Filter {
    private Filter nextFilter;

    public Filter setNextFilter(Filter filter) {
        nextFilter = filter;
        return nextFilter;
    }

    public abstract boolean doFilter(File file);

    public boolean performFiltering(File file) {
        boolean result = doFilter(file);
        if (result && nextFilter != null) {
            result = nextFilter.performFiltering(file);
        }
        return result;
    }


}
