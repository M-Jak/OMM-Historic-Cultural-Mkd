package com.omm.prototype.model.pipeAndFilter.filterImpl;

import com.omm.prototype.model.pipeAndFilter.Filter;

public class TourismFilter implements Filter<String> {
    // Static instance variable for singleton
    private static TourismFilter instance;

    // Private constructor to prevent instantiation
    private TourismFilter() {
        // Optional: Initialization code if needed
    }

    // Static method to get the singleton instance
    public static TourismFilter getInstance() {
            synchronized (TourismFilter.class) {
                if (instance == null) {
                    instance = new TourismFilter();
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

        if (parts[1].equals("museum")) {
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        } else if (parts[1].equals("artwork")) {
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }
        return result.toString();
    }
}
