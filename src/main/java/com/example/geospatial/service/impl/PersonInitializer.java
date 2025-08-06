package com.example.geospatial.service.impl;


import com.example.geospatial.entites.Person;
import com.example.geospatial.repository.PersonStorage;
import com.example.geospatial.service.ProcessService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PersonInitializer {

    private final PersonStorage personStorage;
    private final ProcessService processService;

    @PostConstruct
    public void init() {
        personStorage.save(Person.builder()
                .id(1)
                .name("João")
                .dtBirth(LocalDate.of(1977, 8, 7))
                .dtAdmission(LocalDate.of(2020, 8, 7))
                .build());

        personStorage.save(Person.builder()
                .id(2)
                .name("Maria")
                .dtBirth(LocalDate.of(1971, 3, 21))
                .dtAdmission(LocalDate.of(2023, 10, 1))
                .build());

        personStorage.save(Person.builder()
                .id(3)
                .name("Carlos")
                .dtBirth(LocalDate.of(1980, 2, 12))
                .dtAdmission(LocalDate.of(2022, 1, 7))
                .build());

        processService.updateAllSalaries();
    }
}
