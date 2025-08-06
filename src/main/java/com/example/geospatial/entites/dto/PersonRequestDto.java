package com.example.geospatial.entites.dto;

import com.example.geospatial.entites.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDto {

    private int id;
    private String name;
    private LocalDate dtBirth;

    public static Person toEntity(PersonRequestDto dto) {
        if (dto == null) return null;

        return Person.builder()
                .id(dto.id)
                .name(dto.name)
                .dtBirth(dto.dtBirth)
                .build();
    }
}
