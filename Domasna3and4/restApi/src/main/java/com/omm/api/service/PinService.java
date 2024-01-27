package com.omm.api.service;

import com.omm.api.model.Pin;

import java.util.List;

public interface PinService {

    /**
     * Retrieves all pins from the pin repository.
     * @return A list of all pins stored in the repository.
     */
    List<Pin> getAll();

    /**
     * Retrieves all pins of a specific type from the pin repository.
     * @param type The type of pins to retrieve.
     * @return A list of pins of the specified type stored in the repository.
     */
    List<Pin> getAllByType(String type);

    /**
     * Retrieves all pins filtered by the specified text and type.
     * If the type is null, retrieves all pins whose name or English name contains the specified text,
     * regardless of the type.
     * If the type is not null, retrieves all pins of that type whose name or English name contains the specified text.
     * @param text The text to filter the pins by.
     * @param type The type of pins to filter by. Can be null to retrieve all pins regardless of type.
     * @return A list of Pin objects that match the filter criteria.
     */
    List<Pin> getAllFiltered( String text, String type );

}
