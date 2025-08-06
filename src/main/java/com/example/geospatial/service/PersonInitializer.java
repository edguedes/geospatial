package com.example.geospatial.service;


import com.example.geospatial.entites.Person;
import com.example.geospatial.repository.PersonStorage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PersonInitializer {

    private final PersonStorage personStorage;

    @PostConstruct
    public void init() {
        personStorage.save(Person.builder()
                .id(1)
                .name("João")
                .dtBirth(LocalDate.of(1987, 8, 7))
                .build());

        personStorage.save(Person.builder()
                .id(2)
                .name("Maria")
                .dtBirth(LocalDate.of(1991, 3, 21))
                .build());

        personStorage.save(Person.builder()
                .id(3)
                .name("Carlos")
                .dtBirth(LocalDate.of(2001, 2, 12))
                .build());

        System.out.println("Pessoas iniciais inseridas com sucesso!");
    }
}
