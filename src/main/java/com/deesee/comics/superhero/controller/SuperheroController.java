package com.deesee.comics.superhero.controller;

import com.deesee.comics.superhero.model.Superhero;
import com.deesee.comics.superhero.service.SuperheroService;
import com.deesee.comics.superhero.util.EncryptionUtil;
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
    public List<Superhero> getAllSuperheroes(@RequestParam(required = false) Boolean encrypt) {
        return processHeroes(superheroService.getAllSuperheroes(), encrypt);
    }

    @GetMapping("/by-power")
    public List<Superhero> getSuperheroesByPower(@RequestParam String power, @RequestParam(required = false) Boolean encrypt) {
        return processHeroes(superheroService.getSuperheroesByPower(power), encrypt);
    }

    private List<Superhero> processHeroes(List<Superhero> heroes, Boolean encrypt) {
        if (Boolean.TRUE.equals(encrypt)) {
            return heroes.stream()
                    .map(this::encryptSuperheroIdentity)
                    .collect(Collectors.toList());
        }
        return heroes;
    }

    private Superhero encryptSuperheroIdentity(Superhero hero) {
        String encryptedFirstName = EncryptionUtil.encryptIdentity(hero.getIdentity().getFirstName(), 5);
        String encryptedLastName = EncryptionUtil.encryptIdentity(hero.getIdentity().getLastName(), 5);
        return new Superhero(hero.getName(), new Superhero.Identity(encryptedFirstName, encryptedLastName), hero.getBirthday(), hero.getSuperpowers());
    }
}
