package com.omm.prototype.model.pipeAndFilter;

import java.util.ArrayList;
import java.util.List;

public class Pipe<T> {
    private final List<Filter<T>> filters = new ArrayList<>();

    /**
     * Adds the filter to the pipeline.
     * @param filter The filter to be added
     */
    public void addFilter(Filter<T> filter){
        filters.add(filter);
    }

    /**
     * Runs the input through all filters in the pipeline.
     * @param input The input to be filtered.
     * @return filtered input
     */
    public T runFilters(T input){
        for (Filter<T> filter: filters) {
            input = filter.execute(input);
        }
        return input;
    }
}
