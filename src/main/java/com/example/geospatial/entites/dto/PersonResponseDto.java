package com.example.geospatial.entites.dto;

import com.example.geospatial.entites.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDto {

    @JsonProperty("registro")
    private int id;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("dataNascimento")
    private LocalDate dtBirth;

    @JsonProperty("dataAdmissao")
    private LocalDate dtAdmission;

    public static PersonResponseDto toDto(Person entity) {
        if (entity == null) return null;

        return PersonResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .dtBirth(entity.getDtBirth())
                .dtAdmission(entity.getDtAdmission() != null ? entity.getDtAdmission().toLocalDate() : null)
                .build();
    }
}
