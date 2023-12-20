import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static List<String> doPipe( Pipe<String> pipe, Scanner s ) {
        List<String> filteredInputs=new ArrayList<>();
        while( s.hasNextLine() ) {
            String input=s.nextLine();
            String pipeOutput=pipe.runFilters(input);
            if ( !pipeOutput.isEmpty() ) {
                filteredInputs.add(pipeOutput);
            }
        }
        return filteredInputs;
    }

    public static void main( String[] args ) {
        Pipe<String> amenityPipe=new Pipe<>();
        Pipe<String> historicPipe=new Pipe<>();
        Pipe<String> tourismPipe=new Pipe<>();

        CoordsFilter coordsFilter=new CoordsFilter();

        amenityPipe.addFilter(coordsFilter);
        amenityPipe.addFilter(new LibraryChurchFilter());
        historicPipe.addFilter(coordsFilter);
        historicPipe.addFilter(new ArcheologicFilter());
        tourismPipe.addFilter(coordsFilter);
        tourismPipe.addFilter(new MuseumArtworkFilter());

        List<String> fileNames=new ArrayList<>();
        fileNames.add("./resources/amenity_place_of_worship.csv");
        fileNames.add("./resources/amenity_library.csv");
        fileNames.add("./resources/tourism_artwork.csv");
        fileNames.add("./resources/tourism_museum.csv");
        fileNames.add("./resources/historic_archaeological_site.csv");
        fileNames.add("./resources/historic_memorial.csv");
        fileNames.add("./resources/historic_monument.csv");
        fileNames.add("./resources/historic_tomb.csv");

        List<String> worshipList=new ArrayList<>(), libraryList=new ArrayList<>(), // amenity
                artworkList=new ArrayList<>(), museumList=new ArrayList<>(), // tourism
                archaeologicalList=new ArrayList<>(), memorialList=new ArrayList<>(), monumentList=new ArrayList<>(), tombList=new ArrayList<>(); //historic

        // Run the filter for each CSV file
        for ( String fileName: fileNames ) {
            File file=new File(fileName);
            Scanner s;
            try {
                s=new Scanner(file);
            } catch ( FileNotFoundException e ) {
                System.out.println("Cannot open file. Exiting.");
                return;
            }

            if ( fileName.contains("amenity") ) {
                if ( fileName.contains("worship") )
                    worshipList=doPipe(amenityPipe, s);
                else
                    libraryList=doPipe(amenityPipe, s);
            } else if ( fileName.contains("tourism") ) {
                if ( fileName.contains("artwork") )
                    artworkList=doPipe(tourismPipe, s);
                else
                    museumList=doPipe(tourismPipe, s);
            } else if ( fileName.contains("historic") ) {
                if ( fileName.contains("site") )
                    archaeologicalList=doPipe(historicPipe, s);
                else if ( fileName.contains("memorial") )
                    memorialList=doPipe(historicPipe, s);
                else if ( fileName.contains("monument") )
                    monumentList=doPipe(historicPipe, s);
                else
                    tombList=doPipe(historicPipe, s);
            }

            worshipList.forEach(System.out::println);
            libraryList.forEach(System.out::println);
            artworkList.forEach(System.out::println);
            museumList.forEach(System.out::println);
            archaeologicalList.forEach(System.out::println);
            memorialList.forEach(System.out::println);
            monumentList.forEach(System.out::println);
            tombList.forEach(System.out::println);
        }
    }
}


