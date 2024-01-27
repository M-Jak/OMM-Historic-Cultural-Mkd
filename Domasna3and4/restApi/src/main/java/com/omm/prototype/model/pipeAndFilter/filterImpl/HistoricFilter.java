package com.omm.prototype.model.pipeAndFilter.filterImpl;

import com.omm.prototype.model.pipeAndFilter.Filter;

public class HistoricFilter implements Filter<String> {
    /**
     * Executes the operation based on the input string.
     * If the input contains the word "type" or is empty, returns an empty string.
     * Splits the input string by comma and checks if the second part matches certain predefined values.
     * If the second part matches "memorial", "tomb", "archaeological_site", or "monument",
     * constructs a new string with the first, second, third, and fourth parts separated by commas.
     * @param input The input string to be processed.
     * @return A string containing specific parts of the input string if the second part matches predefined values,
     * or an empty string if conditions are not met.
     */
    @Override
    public String execute(String input) {
        if (input.contains("type") || input.isEmpty()) {
            return "";
        }

        String[] parts = input.split(",", -1);
        StringBuilder result = new StringBuilder();

        if ((parts[1].equals("memorial") || parts[1].equals("tomb")) ||
                (parts[1].equals("archaeological_site") || parts[1].equals("monument"))) {
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }

        return result.toString();
    }
}
