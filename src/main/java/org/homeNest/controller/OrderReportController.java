package org.homeNest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.homeNest.dto.OrderReportDto;
import org.homeNest.exception.OrderReportNotFoundException;
import org.homeNest.service.OrderReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/order-reports")
@RequiredArgsConstructor
public class OrderReportController {

    private final OrderReportService orderReportService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderReport(@PathVariable Long id) throws OrderReportNotFoundException {
        orderReportService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderReportDto> updateOrderReport(
            @PathVariable Long id,
            @Valid @RequestBody OrderReportDto orderReportDto) throws OrderReportNotFoundException {
        OrderReportDto updatedOrderReport = orderReportService.update(id, orderReportDto);
        return ResponseEntity.ok(updatedOrderReport);
    }

    @GetMapping
    public ResponseEntity<Page<OrderReportDto>> getAllOrderReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OrderReportDto> orderReports = orderReportService.getAll(page, size);
        return ResponseEntity.ok(orderReports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderReportDto> getOrderReportById(@PathVariable Long id) throws OrderReportNotFoundException {
        OrderReportDto orderReport = orderReportService.getById(id);
        return ResponseEntity.ok(orderReport);
    }
}