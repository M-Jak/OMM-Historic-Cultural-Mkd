package com.omm.prototype.repository;

import com.omm.prototype.model.Pin;
import com.omm.prototype.model.pipeAndFilter.*;
import com.omm.prototype.model.pipeAndFilter.filterImpl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class PinRepository {
    private final Filter<String> coordsFilter = new CoordsFilter();
    private final Filter<String> archaeologicalSiteFilter = new ArchaeologicalSiteFilter();
    private final Filter<String> artworkFilter = new ArtworkFilter();
    private final Filter<String> libraryFilter = new LibraryFilter();
    private final Filter<String> memorialFilter = new MemorialFilter();
    private final Filter<String> monumentFilter = new MonumentFilter();
    private final Filter<String> tombFilter = new TombFilter();
    private final Filter<String> tourismFilter = new TourismFilter();
    private final Filter<String> worshipFilter = new WorshipFilter();
    private final Filter<String> historicFilter = new HistoricFilter();
    private final Filter<String> amenityFilter = new AmenityFilter();
    private final Filter<String> museumFilter = new MuseumFilter();

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
        allPipe.addFilter(coordsFilter);
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
        typePipe.addFilter(coordsFilter);

        switch (type) {
            case "archaeological_site" -> typePipe.addFilter(archaeologicalSiteFilter);
            case "artwork" -> typePipe.addFilter(artworkFilter);
            case "library" -> typePipe.addFilter(libraryFilter);
            case "memorial" -> typePipe.addFilter(memorialFilter);
            case "monument" -> typePipe.addFilter(monumentFilter);
            case "tomb" -> typePipe.addFilter(tombFilter);
            case "tourism" -> typePipe.addFilter(tourismFilter);
            case "worship" -> typePipe.addFilter(worshipFilter);
            case "historic" -> typePipe.addFilter(historicFilter);
            case "amenity" -> typePipe.addFilter(amenityFilter);
            case "museum" -> typePipe.addFilter(museumFilter);
        }
        List<Pin> pins = new ArrayList<>();
        try {
            pins = processScannerInput(typePipe);
        }
        catch( IOException e){
            System.out.println("data.csv could not be found");
            //System.exit(1);
        }           return pins;
    }
}
