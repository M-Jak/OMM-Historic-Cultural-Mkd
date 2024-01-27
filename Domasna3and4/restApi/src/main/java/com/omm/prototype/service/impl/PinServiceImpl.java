package com.omm.prototype.service.impl;

import com.omm.prototype.model.Pin;
import com.omm.prototype.repository.PinRepository;
import com.omm.prototype.service.PinService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PinServiceImpl implements PinService {

    private final PinRepository repository;
    /**
     * Retrieves all pins from the pin repository.
     * @return A list of all pins stored in the repository.
     */
    @Override
    public List<Pin> getAll() {
        return repository.getAll();
    }

    /**
     * Retrieves all pins of a specific type from the pin repository.
     * @param type The type of pins to retrieve.
     * @return A list of pins of the specified type stored in the repository.
     */
    @Override
    public List<Pin> getAllByType(String type) {
        return repository.getAllByType(type);
    }

    /**
     * Retrieves all pins filtered by the specified text and type.
     * If the type is null, retrieves all pins whose name or English name contains the specified text,
     * regardless of the type.
     * If the type is not null, retrieves all pins of that type whose name or English name contains the specified text.
     * @param text The text to filter the pins by.
     * @param type The type of pins to filter by. Can be null to retrieve all pins regardless of type.
     * @return A list of Pin objects that match the filter criteria.
     */
    @Override
    public List<Pin> getAllFiltered( String text, String type ) {
        if(type == null) {
            return repository.getAll().stream()
                    .filter(p -> {
                if ( p.getName().toLowerCase().contains(text.toLowerCase()) )
                    return true;
                return p.getEn_name().toLowerCase().contains(text.toLowerCase());
            }).toList();
        }
        List<Pin> pinsByType = repository.getAllByType(type);
        return pinsByType.stream()
                .filter(p -> {
                    if ( p.getName().toLowerCase().contains(text.toLowerCase()) )
                        return true;
                    return p.getEn_name().toLowerCase().contains(text.toLowerCase());
                }).toList();
    }

}
