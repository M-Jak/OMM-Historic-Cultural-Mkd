import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {


        ClassLoader loader = Main.class.getClassLoader();
        Scanner s=new Scanner(System.in);
        String pipeOutput;

        File f = new File("./resources/amenity_library.csv");
        if(f.exists()){
            s = new Scanner(f);
        }

        CoordsFilter coordsFilter = new CoordsFilter();
        Pipe<String> libraryPipe = new Pipe<>();
        libraryPipe.addFilter(coordsFilter);

        while( s.hasNextLine() ){
            pipeOutput = libraryPipe.runFilters(s.nextLine());
            System.out.println(pipeOutput);
        }


    }
}