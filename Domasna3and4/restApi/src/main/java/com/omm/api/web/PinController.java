package com.omm.api.web;

import com.omm.api.model.Pin;
import com.omm.api.service.PinService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/omm/api")
@Validated
@CrossOrigin(origins="*")
@AllArgsConstructor
public class PinController {
    private final PinService service;
    /**
     * Retrieves all pins.
     * @return ResponseEntity with a list of all pins and HttpStatus.OK if successful.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Pin>> getAll(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
    /**
     * Retrieves all pins of a specific type.
     * @param type The type of pins to retrieve.
     * @return ResponseEntity with a list of pins of the specified type and HttpStatus.OK if successful.
     */
    @GetMapping("/{type}")
    public ResponseEntity<List<Pin>> getAllByType(@PathVariable String type){
        return new ResponseEntity<>(service.getAllByType(type),HttpStatus.OK);
    }

    /**
     * Retrieves all pins filtered by the specified text and type.
     * If the type is null, retrieves all pins whose name or English name contains the specified text,
     * regardless of the type.
     * If the type is not null, retrieves all pins of that type whose name or English name contains the specified text.
     * @param text The text to filter the pins by.
     * @param type The type of pins to filter by. Can be null to retrieve all pins regardless of type.
     * @return ResponseEntity with a list of Pin objects that match the filter criteria and HttpStatus.OK if successful.
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Pin>> getAllWithFilter(@RequestParam String text, @RequestParam(required=false) String type){
        List<Pin> pins = service.getAllFiltered(text, type);
        return new ResponseEntity<>(pins,HttpStatus.OK);
    }

}
