
public class HistoricFilter implements Filter<String>{
    @Override
    public String execute( String input ) {
        if(input.contains("amenity") || input.isEmpty())
            return "";

        String[] parts = input.split(",", -1);
        StringBuilder result = new StringBuilder();

        //historic:memorial = name,historic,name:en,WKT
        //historic:tomb = name,historic,name:en,WKT
        if(parts[1].equals("memorial") || parts[1].equals("tomb")){
            result.append(parts[0]).append(",");
            result.append(parts[1]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]);
        }
        //historic:archaeological_site = name,tourism,historic,name:en,WKT
        //historic:monument = name,tourism,historic,name:en,WKT
        else if(parts[2].equals("archaeological_site") || parts[2].equals("monument")){
            result.append(parts[0]).append(",");
            result.append(parts[2]).append(",");
            result.append(parts[3]).append(",");
            result.append(parts[4]);
        }
        return result.toString();
    }
}
