package com.example.geospatial.controller;

import com.example.geospatial.entites.dto.PersonRequestDto;
import com.example.geospatial.entites.dto.PersonResponseDto;
import com.example.geospatial.service.ProcessService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final ProcessService processService;

    public PersonController(ProcessService processService) {
        this.processService = processService;
    }


    @PostMapping
    public ResponseEntity<PersonResponseDto> create(@Valid @RequestBody PersonRequestDto dto) {
        PersonResponseDto response = processService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PersonResponseDto>> listAllSortedByName() {
        List<PersonResponseDto> people = processService.listSortedByName();
        return ResponseEntity.ok(people);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        processService.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDto> update(@PathVariable Integer id,
                                                    @RequestBody @Valid PersonRequestDto requestDto) {
        PersonResponseDto updated = processService.update(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponseDto> updateName(@PathVariable Integer id,
                                                        @RequestBody Map<String, String> body) {
        String newName = body.get("name");
        PersonResponseDto updated = processService.updateName(id, newName);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDto> findById(@PathVariable Integer id) {
        PersonResponseDto dto = processService.findById(id);
        return ResponseEntity.ok(dto);
    }

}
