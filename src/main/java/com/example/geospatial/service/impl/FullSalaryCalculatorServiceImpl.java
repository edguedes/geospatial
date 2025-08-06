package com.example.geospatial.service.impl;

import com.example.geospatial.service.SalaryCalculatorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class FullSalaryCalculatorServiceImpl implements SalaryCalculatorService {

    @Override
    public String calculate(LocalDate admissionDate) {
        BigDecimal salary = SalaryCalculationEngine.calculateSalary(admissionDate);
        return "R$ " + salary;
    }
}
