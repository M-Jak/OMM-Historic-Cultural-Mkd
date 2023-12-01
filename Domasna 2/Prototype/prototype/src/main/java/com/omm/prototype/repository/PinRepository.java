package com.omm.prototype.repository;

import com.omm.prototype.model.Pin;
import com.omm.prototype.model.pipeAndFilter.*;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class PinRepository {
    private final CoordsFilter coordsFilter = new CoordsFilter();
    private final ArchaeologicalSiteFilter archaeologicalSiteFilter = new ArchaeologicalSiteFilter();
    private final ArtworkFilter artworkFilter = new ArtworkFilter();
    private final LibraryFilter libraryFilter = new LibraryFilter();
    private final MemorialFilter memorialFilter = new MemorialFilter();
    private final MonumentFilter monumentFilter = new MonumentFilter();
    private final TombFilter tombFilter = new TombFilter();
    private final TourismFilter tourismFilter = new TourismFilter();
    private final WorshipFilter worshipFilter = new WorshipFilter();

    private List<Pin> processScannerInput(Pipe<String> pipe) throws FileNotFoundException {
        Scanner s = new Scanner(new File("C:\\Users\\marin\\Desktop\\Marino\\Finki\\DIANS-Proekt\\Domasna 2\\Prototype\\prototype\\database\\data.csv"));
        List<Pin> pins = new ArrayList<>();
        int counter = 0;
        while (s.hasNextLine()) {
            String input = pipe.runFilters(s.nextLine());
            if (input.isEmpty())
                continue;
            String[] parts = input.split(",");
            pins.add(new Pin(parts[0], parts[1], Double.parseDouble(parts[3].split(" ")[0]), Double.parseDouble(parts[3].split(" ")[1])));
            if(counter++==180)
                continue;
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
            System.exit(1);
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
