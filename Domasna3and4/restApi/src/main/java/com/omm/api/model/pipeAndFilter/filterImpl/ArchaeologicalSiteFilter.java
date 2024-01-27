package com.omm.api.model.pipeAndFilter.filterImpl;

import com.omm.api.model.pipeAndFilter.Filter;

public class ArchaeologicalSiteFilter implements Filter<String> {
    private static ArchaeologicalSiteFilter instance;

    private ArchaeologicalSiteFilter() {
    }

    public static ArchaeologicalSiteFilter getInstance() {
            synchronized (ArchaeologicalSiteFilter.class) {
                if (instance == null) {
                    instance = new ArchaeologicalSiteFilter();
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

        if (parts[1].equals("archaeological_site")) {
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }

        return result.toString();
    }
}
