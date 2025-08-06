package com.example.geospatial.factory;

import com.example.geospatial.service.SalaryCalculatorService;
import com.example.geospatial.service.impl.FullSalaryCalculatorServiceImpl;
import com.example.geospatial.service.impl.SalaryCalculatorServiceImpl;
import com.example.geospatial.service.impl.SalaryInMinimumCalculatorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SalaryCalculatorFactory {

    public SalaryCalculatorService getCalculator(String output) {
        return switch (output.toLowerCase()) {
            case "full" -> new FullSalaryCalculatorServiceImpl();
            case "min" -> new SalaryInMinimumCalculatorServiceImpl();
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid output format");
        };
    }
}
