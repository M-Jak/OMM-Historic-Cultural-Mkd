package com.omm.prototype.web;

import com.omm.prototype.model.Pin;
import com.omm.prototype.service.PinService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/omm/api")
@Validated
@CrossOrigin(origins="*")
@AllArgsConstructor
public class PinController {
    private final PinService service;
    @GetMapping("/all")
    public ResponseEntity<List<Pin>> getAll(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{type}")
    public ResponseEntity<List<Pin>> getAllByType(@PathVariable String type){
        return new ResponseEntity<>(service.getAllByType(type),HttpStatus.OK);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Pin>> getAllFiltered(@RequestParam String text){
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }
}
