package com.omm.api.model.pipeAndFilter;

public interface Filter<T> {
    /**
     * Executes the operation based on the input string.
     * If the input contains the word "type" or is empty, returns an empty string.
     * Splits the input string by comma and checks if the second part matches certain predefined values.
     * If the second part matches "place_of_worship", "library", "artwork", "museum",
     * "memorial", "tomb", "archaeological_site", or "monument",
     * constructs a new string with the first, second, third, and fourth parts separated by commas.
     * @param input The input string to be processed. Possible values: "place_of_worship", "library", "artwork", "museum",
     *              "memorial", "tomb", "archaeological_site", or "monument",
     * @return A string containing specific parts of the input string, or an empty string if conditions are not met.
     */
    T execute(T input);
}
