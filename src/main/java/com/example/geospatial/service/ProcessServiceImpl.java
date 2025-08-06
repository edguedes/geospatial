package com.example.geospatial.service;

import com.example.geospatial.entites.Person;
import com.example.geospatial.entites.dto.PersonRequestDto;
import com.example.geospatial.entites.dto.PersonResponseDto;
import com.example.geospatial.repository.PersonStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final PersonStorage personStorage;

    @Override
    public PersonResponseDto create(PersonRequestDto requestDto) {
        log.info("Starting person creation: {}", requestDto);
        try {
            Person entity = PersonRequestDto.toEntity(requestDto);
            log.debug("Mapped entity: {}", entity);


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
        try {
            personStorage.deleteById(id);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Override
    public PersonResponseDto updateName(Integer id, String newName) {
        try {
            Person updated = personStorage.updateName(id, newName);
            return PersonResponseDto.toDto(updated);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Override
    public PersonResponseDto findById(Integer id) {
        try {
            Person person = personStorage.findById(id);
            return PersonResponseDto.toDto(person);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
