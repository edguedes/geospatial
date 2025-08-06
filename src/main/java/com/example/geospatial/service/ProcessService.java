package com.example.geospatial.service;

import com.example.geospatial.entites.dto.PersonRequestDto;
import com.example.geospatial.entites.dto.PersonResponseDto;

import java.util.List;

public interface ProcessService {

    PersonResponseDto create(PersonRequestDto requestDto);
    List<PersonResponseDto> listSortedByName();
    void deleteById(Integer id);
    PersonResponseDto update(Integer id, PersonRequestDto dto);
    PersonResponseDto updateName(Integer id, String newName);
    PersonResponseDto findById(Integer id);
}
