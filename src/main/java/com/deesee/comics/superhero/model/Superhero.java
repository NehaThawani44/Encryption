package com.deesee.comics.superhero.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Superhero {
    private String name;
    private Identity identity;
    private LocalDate birthday;
    private List<String> superpowers;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Identity {
        private String firstName;
        private String lastName;
    }
}