package org.example.coursework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.coursework.dto.OrderReportCreateDto;
import org.example.coursework.dto.OrderReportDto;
import org.example.coursework.service.OrderReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<OrderReportDto>> getAllOrderReports() {
        List<OrderReportDto> orderReports = orderReportService.getAll();
        return ResponseEntity.ok(orderReports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderReportDto> getOrderReportById(@PathVariable Long id) {
        OrderReportDto orderReport = orderReportService.getById(id);
        return ResponseEntity.ok(orderReport);
    }
}