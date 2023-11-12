public class TourismFilter implements Filter<String>{
    @Override
    public String execute( String input ) {
        if(input.contains("amenity") || input.isEmpty())
            return "";

        String[] parts = input.split(",", -1);
        StringBuilder result = new StringBuilder();

        //tourism:museum = name,tourism,name:en,WKT
        if(parts[1].equals("museum")) {
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }
        //tourism:artwork = name,tourism,historic,name:en,WKT
        else if(parts[1].equals("artwork")){
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[3]).append(",");
            result.append(parts[4]);
        }
        return result.toString();
    }
}
