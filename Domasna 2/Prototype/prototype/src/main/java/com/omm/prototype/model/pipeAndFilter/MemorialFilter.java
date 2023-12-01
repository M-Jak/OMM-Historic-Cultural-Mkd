package com.omm.prototype.model.pipeAndFilter;

public class MemorialFilter implements Filter<String> {
    @Override
    public String execute(String input) {
        String[] parts = input.split(",", -1);
        StringBuilder result = new StringBuilder();

        if (parts[1].equals("memorial")) {
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }

        return result.toString();
    }
}
