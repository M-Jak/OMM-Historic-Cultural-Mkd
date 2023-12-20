package com.omm.prototype.model.pipeAndFilter;

public interface Filter<T> {
    T execute(T input);
}
