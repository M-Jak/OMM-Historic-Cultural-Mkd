package com.omm.prototype.repository;

import com.omm.prototype.model.Pin;
import com.omm.prototype.model.pipeAndFilter.Filter;
import com.omm.prototype.model.pipeAndFilter.Pipe;
import com.omm.prototype.model.pipeAndFilter.filterImpl.*;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class PinRepository {

    /**
     * Processes the input from a scanner using the specified pipe of filters.
     * Reads input from the data.csv file, applies the filters in the provided pipe,
     * constructs Pin objects from the filtered input, and returns a list of Pins.
     * @param pipe The pipe containing filters to apply to the input.
     * @return A list of Pin objects constructed from the filtered input.
     * @throws IOException if an I/O error occurs while reading the data.csv file.
     */
    private List<Pin> processScannerInput(Pipe<String> pipe) throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("database/data.csv");

        assert inputStream != null;
        Scanner s = new Scanner(inputStream);
        List<Pin> pins = new ArrayList<>();
        while (s.hasNextLine()) {
            String input = pipe.runFilters(s.nextLine());
            if (input.isEmpty())
                continue;
            String[] parts = input.split(",");
            pins.add(new Pin(parts[0], parts[1], parts[2], Double.parseDouble(parts[3].split(" ")[0]), Double.parseDouble(parts[3].split(" ")[1])));
        }
        s.close();
        return pins;
    }

    /**
     * Retrieves all pins.
     * Applies coordinate filtering to all pins.
     * @return A list of all pins stored in the database.
     */
    public List<Pin> getAll() {
        Pipe<String> allPipe = new Pipe<>();
        allPipe.addFilter(CoordsFilter.getInstance());
        List<Pin> pins = new ArrayList<>();
        try {
            pins = processScannerInput(allPipe);
        }
        catch( IOException e){
            System.out.println("data.csv could not be found");
        }
        return pins;
    }

    /**
     * Retrieves all pins of a specific type.
     * Applies coordinate filtering and type-specific filtering to the pins.
     * @param type The type of pins to retrieve.
     * @return A list of pins of the specified type stored in the database.
     */
    public List<Pin> getAllByType(String type) {
        Pipe<String> typePipe = new Pipe<>();
        typePipe.addFilter(CoordsFilter.getInstance()); // Create instance here

        switch (type) {
            case "archaeological_site": typePipe.addFilter(ArchaeologicalSiteFilter.getInstance());
                break;
            case "artwork": typePipe.addFilter(ArtworkFilter.getInstance());
                break;
            case "library": typePipe.addFilter(LibraryFilter.getInstance());
                break;
            case "memorial": typePipe.addFilter(MemorialFilter.getInstance());
                break;
            case "monument": typePipe.addFilter(MonumentFilter.getInstance());
                break;
            case "tomb": typePipe.addFilter(TombFilter.getInstance());
                break;
            case "tourism": typePipe.addFilter(TourismFilter.getInstance());
                break;
            case "worship": typePipe.addFilter(WorshipFilter.getInstance());
                break;
            case "historic": typePipe.addFilter(HistoricFilter.getInstance());
                break;
            case "amenity": typePipe.addFilter(AmenityFilter.getInstance());
                break;
            case "museum": typePipe.addFilter(MuseumFilter.getInstance());
                break;
        }
        List<Pin> pins = new ArrayList<>();
        try {
            pins = processScannerInput(typePipe);
        }
        catch( IOException e){
            System.out.println("data.csv could not be found");
        }
        return pins;
    }
}
