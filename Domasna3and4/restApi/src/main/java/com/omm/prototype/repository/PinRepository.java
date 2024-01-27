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
            //System.exit(1);
        }           return pins;
    }
}
