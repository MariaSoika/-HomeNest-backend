package org.example.coursework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.coursework.dto.RentCreateDto;
import org.example.coursework.dto.RentDto;
import org.example.coursework.service.RentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rent")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @PostMapping
    public ResponseEntity<RentDto> create(@Valid @RequestBody RentCreateDto rentCreateDto) {
        return new ResponseEntity<>(rentService.create(rentCreateDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RentDto> delete(@PathVariable Long id) {
        rentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentDto> update(
            @PathVariable Long id,
            @Valid @RequestBody RentDto rentDto) {
       return new ResponseEntity<>(rentService.update(id, rentDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<RentDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<RentDto> rents = rentService.getAll(page, size);
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentDto> getId(@PathVariable Long id) {
        RentDto rent = rentService.getById(id);
        return ResponseEntity.ok(rent);
    }
}
