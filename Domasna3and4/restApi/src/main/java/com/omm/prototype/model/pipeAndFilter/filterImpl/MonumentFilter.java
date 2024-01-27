package com.omm.prototype.model.pipeAndFilter.filterImpl;

import com.omm.prototype.model.pipeAndFilter.Filter;

public class MonumentFilter implements Filter<String> {
    // Static instance variable for singleton
    private static MonumentFilter instance;

    // Private constructor to prevent instantiation
    private MonumentFilter() {
        // Optional: Initialization code if needed
    }

    // Static method to get the singleton instance
    public static MonumentFilter getInstance() {
            synchronized (MonumentFilter.class) {
                if (instance == null) {
                    instance = new MonumentFilter();
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

        if (parts[1].equals("monument")) {
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }

        return result.toString();
    }
}
