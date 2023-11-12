public class CoordsFilter implements Filter<String>{
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
        }
        else {
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
