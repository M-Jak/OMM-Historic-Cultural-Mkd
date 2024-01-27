package com.omm.api.model.pipeAndFilter.filterImpl;

import com.omm.api.model.pipeAndFilter.Filter;

public class CoordsFilter implements Filter<String> {
    private static CoordsFilter instance;

    private CoordsFilter() {
    }

    public static CoordsFilter getInstance() {
            synchronized (CoordsFilter.class) {
                if (instance == null) {
                    instance = new CoordsFilter();
                }
            }
        return instance;
    }

    /**
     * Parses the input string to extract coordinates and constructs a new string with modified formatting.
     * If the last part of the input contains "WKT", returns an empty string.
     * Otherwise, extracts coordinates from the input string and restructures them.
     * If the coordinates are in the format "POINT", it extracts longitude and latitude.
     * If not, it handles the alternative format, removing unnecessary characters and extracting lon-lat.
     * Constructs a new string with modified formatting by appending non-empty parts of the input and the coordinates.
     * @param input The input string containing coordinates to be processed.
     * @return A string with modified formatting containing extracted coordinates, or an empty string if the input ends with "WKT".
     */
    @Override
    public String execute( String input ) {
        String[] parts = input.split(",");
        if(parts[parts.length-1].equals("WKT"))
            return "";
        String coords = parts[parts.length-1];
        StringBuilder result =new StringBuilder();
        String[] lonLat;
        if(coords.contains("POINT")){
            lonLat=coords.replace("POINT (", "")
                    .replace(")", "")
                    .split("\\s++");
        } else { // if it contains "POLYGON"
            String test = coords.replace("))\"", "");
            test = test.strip();
            lonLat=test.split("\\s++");
            int index = input.indexOf("\"");
            input = input.substring(0,index);
            parts = input.split(",", -1);
        }
        String coordinates= lonLat[1]+" "+lonLat[0];
        for ( int i=0; i < parts.length-1; i++ ) {
            String part = parts[i];
            if(!part.isEmpty())
                result.append(part)
                    .append(",");
            else result.append(",");
        }
        result.append(coordinates);
        return result.toString();
    }
}
