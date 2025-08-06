package com.example.geospatial.entites.dto;

import com.example.geospatial.entites.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @JsonProperty("data de Nascimento")
    private LocalDate dtBirth;

    @JsonProperty("data de Admissão")
    private LocalDate dtAdmission;

    @JsonProperty("data Atual")
    private LocalDate dtNow;

    @JsonProperty("salario")
    private BigDecimal salary;

    public static PersonResponseDto toDto(Person entity) {
        if (entity == null) return null;

        return PersonResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .dtBirth(entity.getDtBirth())
                .dtAdmission(entity.getDtAdmission())
                .dtNow(LocalDate.now())
                .salary(entity.getSalary())
                .build();
    }
}
