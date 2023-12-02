package com.omm.prototype.repository;

import com.omm.prototype.model.Pin;
import com.omm.prototype.model.pipeAndFilter.*;
import com.omm.prototype.model.pipeAndFilter.filterImpl.*;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
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

    private List<Pin> processScannerInput(Pipe<String> pipe) throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/main/resources/database/data.csv"));
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

    public List<Pin> getAll() {
        Pipe<String> allPipe = new Pipe<>();
        allPipe.addFilter(coordsFilter);
        List<Pin> pins = new ArrayList<>();
        try {
            pins = processScannerInput(allPipe);
        }
        catch(FileNotFoundException e){
            System.out.println("data.csv could not be found");
        }
        return pins;
    }

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
        catch(FileNotFoundException e){
            System.out.println("data.csv could not be found");
            System.exit(1);
        }        return pins;
    }
}
