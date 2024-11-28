package org.example.coursework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.coursework.dto.OrderReportCreateDto;
import org.example.coursework.dto.OrderReportDto;
import org.example.coursework.service.OrderReportService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/api/order-reports")
@RequiredArgsConstructor
public class OrderReportController {

    private final OrderReportService orderReportService;

    @PostMapping
    public ResponseEntity<OrderReportDto> createOrderReport(@Valid @RequestBody OrderReportCreateDto orderReportCreateDto) {
        OrderReportDto createdOrderReport = orderReportService.create(orderReportCreateDto);
        return new ResponseEntity<>(createdOrderReport, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderReport(@PathVariable Long id) {
        orderReportService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderReportDto> updateOrderReport(
            @PathVariable Long id,
            @Valid @RequestBody OrderReportDto orderReportDto) {
        OrderReportDto updatedOrderReport = orderReportService.update(id, orderReportDto);
        return ResponseEntity.ok(updatedOrderReport);
    }

    @GetMapping
    public ResponseEntity<Page<OrderReportDto>> getAllOrderReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderReportDto> orderReports = orderReportService.getAll(page, size);
        return ResponseEntity.ok(orderReports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderReportDto> getOrderReportById(@PathVariable Long id) {
        OrderReportDto orderReport = orderReportService.getById(id);
        return ResponseEntity.ok(orderReport);
    }
}