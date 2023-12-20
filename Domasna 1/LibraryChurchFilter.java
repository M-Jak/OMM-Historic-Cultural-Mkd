
public class LibraryChurchFilter implements Filter<String>{
    @Override
    public String execute( String input ) {
        if(input.contains("type") || input.isEmpty())
            return "";

        String[] parts = input.split(",", -1);
        StringBuilder result = new StringBuilder();
        //amenity:library = name,type,name:en,WKT
        if(parts[1].equals("library")){
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }
        //amenity:worship = name,amenity,historic,name:en,WKT
        else if(parts[1].equals("place_of_worship")){
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }

        return result.toString();

    }
}
