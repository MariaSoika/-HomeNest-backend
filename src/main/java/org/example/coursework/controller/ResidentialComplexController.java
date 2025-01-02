package org.example.coursework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.coursework.dto.ResidentialComplexDto;
import org.example.coursework.exception.UserNotFoundException;
import org.example.coursework.service.ResidentialComplexService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/residentialComplexes")
@RequiredArgsConstructor
public class ResidentialComplexController {

    private final ResidentialComplexService residentialComplexService;

    @PostMapping
    public ResponseEntity<ResidentialComplexDto> create(@Valid @RequestBody ResidentialComplexDto residentialComplexDto) {
        ResidentialComplexDto created = residentialComplexService.create(residentialComplexDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResidentialComplexDto> delete(@PathVariable Long id) {
        residentialComplexService.delete(id);
        //TODO change delete logic
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResidentialComplexDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ResidentialComplexDto residentialComplexDto) {
        ResidentialComplexDto update = residentialComplexService.update(id, residentialComplexDto);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ResidentialComplexDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ResidentialComplexDto> residentialComplexes = residentialComplexService.getAll(page, size);
        return ResponseEntity.ok(residentialComplexes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResidentialComplexDto> getUserById(@PathVariable Long id) throws UserNotFoundException {
        ResidentialComplexDto residentialComplex = residentialComplexService.getById(id);
        return ResponseEntity.ok(residentialComplex);
    }
}
