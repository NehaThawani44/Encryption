package com.deesee.comics.superhero.controller;

import com.deesee.comics.superhero.model.Superhero;
import com.deesee.comics.superhero.service.SuperheroService;
import com.deesee.comics.superhero.util.EncryptionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

    private ObjectMapper objectMapper;
    private List<Superhero> sampleSuperheroes;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        sampleSuperheroes = Arrays.asList(
                new Superhero("Superman", new Superhero.Identity("Clark", "Kent"), LocalDate.parse("1977-04-18"), Arrays.asList("strength", "flight", "invulnerability")),
                new Superhero("Batman", new Superhero.Identity("Bruce", "Wayne"), LocalDate.parse("1915-04-17"), Arrays.asList("strength", "invulnerability"))
        );
    }

    @Test
    void testGetAllSuperheroesWithEncryption() throws Exception {
        given(superheroService.getAllSuperheroes()).willReturn(sampleSuperheroes);
        String expectedJson = "[\"" + EncryptionUtil.encryptIdentity("Clark Kent", 5) + "\", \"" + EncryptionUtil.encryptIdentity("Bruce Wayne", 5) + "\"]";

        mockMvc.perform(get("/api/superheroes")
                        .param("encrypt", "true")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testGetAllSuperheroesWithoutEncryption() throws Exception {
        given(superheroService.getAllSuperheroes()).willReturn(sampleSuperheroes);
        String expectedJson = "[\"Clark Kent\", \"Bruce Wayne\"]";

        mockMvc.perform(get("/api/superheroes")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testGetSuperheroesByPowerWithEncryption() throws Exception {
        given(superheroService.getSuperheroesByPower("strength")).willReturn(sampleSuperheroes);
        String expectedJson = "[\"" + EncryptionUtil.encryptIdentity("Clark Kent", 5) + "\", \"" + EncryptionUtil.encryptIdentity("Bruce Wayne", 5) + "\"]";

        mockMvc.perform(get("/api/superheroes/by-power")
                        .param("power", "strength")
                        .param("encrypt", "true")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testGetSuperheroesByPowerWithoutEncryption() throws Exception {
        given(superheroService.getSuperheroesByPower("strength")).willReturn(sampleSuperheroes);
        String expectedJson = "[\"Clark Kent\", \"Bruce Wayne\"]";

        mockMvc.perform(get("/api/superheroes/by-power")
                        .param("power", "strength")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void testAddSuperhero() throws Exception {

        Superhero superhero = new Superhero("Spiderman", new Superhero.Identity("Peter", "Parker"), LocalDate.parse("1962-08-01"), List.of("agility", "web-slinging"));
        String requestBody = objectMapper.writeValueAsString(superhero);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());


        Mockito.verify(superheroService).addSuperhero(Mockito.any(Superhero.class));
    }


}
