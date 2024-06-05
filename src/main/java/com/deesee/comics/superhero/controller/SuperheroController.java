package com.deesee.comics.superhero.controller;

import com.deesee.comics.superhero.model.Superhero;
import com.deesee.comics.superhero.service.SuperheroService;
import com.deesee.comics.superhero.util.EncryptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/superheroes")
public class SuperheroController {


    @Autowired
    private SuperheroService superheroService;

    @GetMapping
    public List<String> getAllSuperheroes(@RequestParam(required = false) Boolean encrypt) {
        return processHeroes(superheroService.getAllSuperheroes(), encrypt);
    }

    @GetMapping("/by-power")
    public List<String> getSuperheroesByPower(@RequestParam String power, @RequestParam(required = false) Boolean encrypt) {
        return processHeroes(superheroService.getSuperheroesByPower(power), encrypt);
    }

    private List<String> processHeroes(List<Superhero> heroes, Boolean encrypt) {
        return heroes.stream()
                .map(hero -> formatIdentity(hero, encrypt))
                .collect(Collectors.toList());
    }

    private String formatIdentity(Superhero hero, Boolean encrypt) {
        String identity = hero.getIdentity().getFirstName() + " " + hero.getIdentity().getLastName();
        if (Boolean.TRUE.equals(encrypt)) {
            return EncryptionUtil.encryptIdentity(identity, 5);  // Using a fixed shift of 5 for this example
        }
        return identity;
    }

    @PostMapping
    public ResponseEntity<String> addSuperhero(@RequestBody Superhero superhero) {
        superheroService.addSuperhero(superhero);
        return ResponseEntity.status(HttpStatus.CREATED).body("Superhero added successfully");
    }

}

