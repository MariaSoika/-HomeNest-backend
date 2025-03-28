package org.homeNest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.homeNest.dto.HouseCreateDto;
import org.homeNest.dto.HouseDto;
import org.homeNest.exception.HouseNotFoundException;
import org.homeNest.service.HouseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @PostMapping
    public ResponseEntity<HouseDto> createHouse(@Valid @RequestBody HouseCreateDto houseCreateDto) {
        return new ResponseEntity<>(houseService.create(houseCreateDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@Valid @PathVariable Long id) throws HouseNotFoundException {
        houseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseDto> updateHouse(
            @PathVariable Long id,
            @Valid @RequestBody HouseDto houseUpdateDto) throws HouseNotFoundException {
        return new ResponseEntity<>(houseService.update(id, houseUpdateDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<HouseDto>> getAllApartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(houseService.getAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseDto> getHouse(@PathVariable Long id) throws HouseNotFoundException {
        return ResponseEntity.ok(houseService.getById(id));
    }
}
