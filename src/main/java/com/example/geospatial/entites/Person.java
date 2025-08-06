package com.example.geospatial.entites;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Date Birth is required")
    private LocalDate dtBirth ;

    private LocalDateTime dtAdmission ;

    public void prePersist() {
        if (dtAdmission == null) {
            dtAdmission = LocalDateTime.now();
        }
    }

}
