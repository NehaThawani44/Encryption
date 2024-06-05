package com.deesee.comics.superhero.controller;

import com.deesee.comics.superhero.model.Superhero;
import com.deesee.comics.superhero.service.SuperheroService;
import com.deesee.comics.superhero.util.EncryptionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SuperheroController.class)
class SuperheroControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperheroService superheroService;

    private List<Superhero> sampleSuperheroes;

    @BeforeEach
    void setUp() {
        sampleSuperheroes = Arrays.asList(
                new Superhero("Superman", new Superhero.Identity("Clark", "Kent"), LocalDate.parse("1977-04-18"), List.of("flight", "strength", "invulnerability")),
                new Superhero("Batman", new Superhero.Identity("Bruce", "Wayne"), LocalDate.parse("1915-04-17"), List.of())
        );
    }

    @Test
    void testGetAllSuperheroes() throws Exception {
        given(superheroService.getAllSuperheroes()).willReturn(sampleSuperheroes);

        mockMvc.perform(get("/api/superheroes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Superman"))
                .andExpect(jsonPath("$[1].name").value("Batman"));
    }

    @Test
    void testGetAllSuperheroesWithEncryption() throws Exception {
        given(superheroService.getAllSuperheroes()).willReturn(sampleSuperheroes);

        mockMvc.perform(get("/api/superheroes?encrypt=true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assert content.contains(EncryptionUtil.encryptIdentity("Clark Kent", 5));
                    assert content.contains(EncryptionUtil.encryptIdentity("Bruce Wayne", 5));
                });
    }

    @Test
    void testGetSuperheroesByPower() throws Exception {
        given(superheroService.getSuperheroesByPower("flight")).willReturn(Arrays.asList(sampleSuperheroes.get(0)));

        mockMvc.perform(get("/api/superheroes/by-power?power=flight")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Superman"))
                .andExpect(jsonPath("$[0].superpowers").isArray())
                .andExpect(jsonPath("$[0].superpowers").value("flight"));
    }
}
