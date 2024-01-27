package com.omm.prototype.model.pipeAndFilter.filterImpl;

import com.omm.prototype.model.pipeAndFilter.Filter;

public class ArtworkFilter implements Filter<String> {
    /**
     * Executes a specific operation on the provided input string.
     * This method checks if the input string contains the word "type" or if it's empty.
     * If either condition is met, an empty string is returned.
     * Otherwise, the input string is split by comma into parts.
     * If the second part of the input string equals "artwork",
     * a new string is constructed containing the first, second, third, and fourth parts separated by commas.
     * @param input The input string to be processed.
     * @return A string containing specific parts of the input string if the second part is "artwork",
     * or an empty string if the input contains "type" or is empty.
     */
    @Override
    public String execute( String input ) {
        if(input.contains("type") || input.isEmpty())
            return "";

        String[] parts = input.split(",", -1);
        StringBuilder result = new StringBuilder();

        if(parts[1].equals("artwork")){
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }
        return result.toString();
    }
}
