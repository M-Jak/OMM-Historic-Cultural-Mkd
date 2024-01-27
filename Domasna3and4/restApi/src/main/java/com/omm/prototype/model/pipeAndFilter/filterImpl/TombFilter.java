package com.omm.prototype.model.pipeAndFilter.filterImpl;

import com.omm.prototype.model.pipeAndFilter.Filter;

public class TombFilter implements Filter<String> {
    private static TombFilter instance;

    private TombFilter() {
    }

    public static TombFilter getInstance() {
            synchronized (TombFilter.class) {
                if (instance == null) {
                    instance = new TombFilter();
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

        if (parts[1].equals("tomb")) {
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }

        return result.toString();
    }
}
