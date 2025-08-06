package com.example.geospatial.service.impl;

import com.example.geospatial.entites.Person;
import com.example.geospatial.entites.dto.PersonRequestDto;
import com.example.geospatial.entites.dto.PersonResponseDto;
import com.example.geospatial.factory.SalaryCalculatorFactory;
import com.example.geospatial.repository.PersonStorage;
import com.example.geospatial.service.ProcessService;
import com.example.geospatial.service.SalaryCalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final PersonStorage personStorage;
    private final SalaryCalculatorFactory salaryCalculatorFactory;

    @Override
    public PersonResponseDto create(PersonRequestDto requestDto) {
        log.info("Starting person creation: {}", requestDto);
        try {
            Person entity = PersonRequestDto.toEntity(requestDto);
            log.debug("Mapped entity: {}", entity);

            if (entity.getDtAdmission() == null) {
                entity.setDtAdmission(LocalDate.now());
            }
            if (entity.getSalary() == null) {
                entity.setSalary(new BigDecimal("1558.00"));
            }

            Person saved = personStorage.save(entity);
            log.info("Person saved successfully. ID: {}", saved.getId());

            return PersonResponseDto.toDto(saved);

        } catch (IllegalArgumentException ex) {
            log.warn("Error saving person: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @Override
    public List<PersonResponseDto> listSortedByName() {
        return personStorage.findAllSortedByName().stream()
                .map(PersonResponseDto::toDto)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        log.info("Trying to delete person with ID: {}", id);
        try {
            personStorage.deleteById(id);
            log.info("Person with ID {} deleted successfully", id);

        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Override
    public PersonResponseDto update(Integer id, PersonRequestDto dto) {
        try {
            Person updated = personStorage.update(id,PersonRequestDto.toEntity(dto));
            return PersonResponseDto.toDto(updated);
        } catch (NoSuchElementException ex) {
            log.warn("Person with ID {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Override
    public PersonResponseDto updateName(Integer id, String newName) {
        log.info("Updating name for person with ID: {} to '{}'", id, newName);
        try {
            Person updated = personStorage.updateName(id, newName);
            log.info("Person with ID {} updated successfully", id);
            return PersonResponseDto.toDto(updated);
        } catch (NoSuchElementException ex) {
            log.warn("Person with ID {} not found for update", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Override
    public PersonResponseDto findById(Integer id) {
        log.info("Searching for person with ID: {}", id);
        try {
            Person person = personStorage.findById(id);
            log.info("Person with ID {} found", id);
            return PersonResponseDto.toDto(person);
        } catch (NoSuchElementException ex) {
            log.warn("Person with ID {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    public String getSalaryByFormat(Integer id, String output) {
        log.info("Calculating salary for person ID {} with output format '{}'", id, output);
        Person person = personStorage.findById(id);

        SalaryCalculatorService calculator = salaryCalculatorFactory.getCalculator(output);
        String result = calculator.calculate(LocalDate.from(person.getDtAdmission()));
        log.info("Salary calculated: {}", result);
        return result;
    }

    public void updateAllSalaries() {
        log.info("Starting salary update for all persons");

        personStorage.findAllSortedByName().forEach(person -> {
            try {
                BigDecimal updatedSalary = SalaryCalculationEngine.calculateSalary(LocalDate.from(person.getDtAdmission()));
                person.setSalary(updatedSalary);
                personStorage.update(person.getId(), person);
                log.info("Updated salary for person ID {} to {}", person.getId(), updatedSalary);
            } catch (Exception e) {
                log.error("Failed to update salary for person ID {}: {}", person.getId(), e.getMessage());
            }
        });
        log.info("Salary update process completed");
    }
}
