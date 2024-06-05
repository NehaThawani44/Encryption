package com.deesee.comics.superhero.service;


import com.deesee.comics.superhero.model.Superhero;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuperheroService {

        private final List<Superhero> superheroes = new ArrayList<>();

        public SuperheroService() {
            superheroes.add(new Superhero("Superman", new Superhero.Identity("Clark", "Kent"), LocalDate.parse("1977-04-18"), List.of("flight", "strength", "invulnerability")));
            superheroes.add(new Superhero("Deadpool", new Superhero.Identity("Wade", "Wilson"), LocalDate.parse("1973-11-22"), List.of("healing")));
            superheroes.add(new Superhero("Batman", new Superhero.Identity("Bruce", "Wayne"), LocalDate.parse("1915-04-17"), List.of()));
            superheroes.add(new Superhero("Aquaman", new Superhero.Identity("Arthur", "Curry"), LocalDate.parse("1986-01-29"), List.of("flight", "healing", "strength")));
            superheroes.add(new Superhero("Flash", new Superhero.Identity("Barry", "Allen"), LocalDate.parse("1992-09-30"), List.of("speed", "healing")));
        }

        public List<Superhero> getAllSuperheroes() {
            return new ArrayList<>(superheroes);
        }

        public List<Superhero> getSuperheroesByPower(String power) {
            return superheroes.stream()
                    .filter(hero -> hero.getSuperpowers().contains(power))
                    .collect(Collectors.toList());
        }
    }

