package com.example.geospatial.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SalaryCalculationEngine {

    public static final BigDecimal INITIAL_SALARY = new BigDecimal("1558.00");
    private static final BigDecimal INCREMENT_PERCENT = new BigDecimal("0.18");
    private static final BigDecimal FIXED_INCREMENT = new BigDecimal("500.00");

    public static BigDecimal calculateSalary(LocalDate admissionDate) {
        LocalDate today = LocalDate.now();
        long years = ChronoUnit.YEARS.between(admissionDate, today);

        BigDecimal currentSalary = INITIAL_SALARY;

        for (int i = 0; i < years; i++) {
            BigDecimal increase = currentSalary.multiply(INCREMENT_PERCENT).add(FIXED_INCREMENT);
            currentSalary = currentSalary.add(increase);
        }

        return currentSalary.setScale(2, RoundingMode.HALF_UP);
    }
}
