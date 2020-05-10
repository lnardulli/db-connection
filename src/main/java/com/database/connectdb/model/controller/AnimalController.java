package com.database.connectdb.model.controller;

import com.database.connectdb.model.Animal;
import com.database.connectdb.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping(path = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Animal> findAnimalById(@PathVariable Integer id) throws SQLException {
        return new ResponseEntity<>(this.animalService.getAnimalById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Integer> findPersonByName(@RequestBody Animal animal) throws SQLException {
        Integer animalId = this.animalService.create(animal);
        return new ResponseEntity<>(animalId, HttpStatus.OK);
    }


}
