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
            lonLat=coords.replace("))\"", "")
                    .strip()
                    .split("\\s++");
        }
        String coordinates= lonLat[1]+" "+lonLat[0];
        for ( int i=0; i < parts.length - 2; i++ ) {
            result.append(parts[i]).append(",");
        }
        result.append(coordinates);
        return result.toString();
    }
}
