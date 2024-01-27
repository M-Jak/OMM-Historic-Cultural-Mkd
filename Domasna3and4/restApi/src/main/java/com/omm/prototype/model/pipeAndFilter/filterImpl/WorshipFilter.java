package com.omm.prototype.model.pipeAndFilter.filterImpl;

import com.omm.prototype.model.pipeAndFilter.Filter;

public class WorshipFilter implements Filter<String> {
    // Static instance variable for singleton
    private static WorshipFilter instance;

    // Private constructor to prevent instantiation
    private WorshipFilter() {
        // Optional: Initialization code if needed
    }

    // Static method to get the singleton instance
    public static WorshipFilter getInstance() {
            synchronized (WorshipFilter.class) {
                if (instance == null) {
                    instance = new WorshipFilter();
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
        if (parts[1].equals("place_of_worship")) {
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }

        return result.toString();
    }
}
