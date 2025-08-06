package com.example.geospatial.service.impl;

import com.example.geospatial.service.SalaryCalculatorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class SalaryInMinimumCalculatorServiceImpl implements SalaryCalculatorService {

    private static final BigDecimal MINIMUM_WAGE = new BigDecimal("1302.00");

    @Override
    public String calculate(LocalDate admissionDate) {
        BigDecimal fullSalary = SalaryCalculationEngine.calculateSalary(admissionDate);
        BigDecimal minCount = fullSalary.divide(MINIMUM_WAGE, 2, RoundingMode.UP);
        return minCount + " salários mínimos";
    }
}
