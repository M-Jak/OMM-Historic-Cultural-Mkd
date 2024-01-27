package com.omm.prototype.model.pipeAndFilter.filterImpl;

import com.omm.prototype.model.pipeAndFilter.Filter;

public class AmenityFilter implements Filter<String> {
    // Static instance variable for singleton
    private static AmenityFilter instance;

    // Private constructor to prevent instantiation
    private AmenityFilter() {
        // Optional: Initialization code if needed
    }

    // Static method to get the singleton instance
    public static AmenityFilter getInstance() {
            synchronized (AmenityFilter.class) {
                if (instance == null) {
                    instance = new AmenityFilter();
                }
            }
        return instance;
    }

    @Override
    public String execute(String input) {
        if (input.contains("type") || input.isEmpty())
            return "";

        String[] parts = input.split(",", -1);
        StringBuilder result = new StringBuilder();
        if (parts[1].equals("place_of_worship") || parts[1].equals("library")
                || parts[1].equals("artwork") || parts[1].equals("museum")) {
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }

        return result.toString();
    }
}
