package com.example.geospatial.entites;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Date Birth is required")
    private LocalDate dtBirth;

    private BigDecimal salary;

    private LocalDate dtAdmission;

}
