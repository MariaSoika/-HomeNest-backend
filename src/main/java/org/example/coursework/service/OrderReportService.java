package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.OrderReportCreateDto;
import org.example.coursework.dto.OrderReportDto;
import org.example.coursework.entity.OrderReport;
import org.example.coursework.mapper.OrderReportMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.example.coursework.repository.OrderReportRepository;
import org.example.coursework.repository.OrderRepository;
import org.example.coursework.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class OrderReportService {

    private final OrderReportMapper orderReportMapper;
    private OrderRepository orderRepository;
    private OrderReportRepository orderReportRepository;

    @Transactional
    public OrderReportDto create(OrderReportCreateDto orderReportCreateDto) {
        OrderReport orderReport = new OrderReport();
        orderReport.setOrder(orderRepository.getReferenceById(orderReportCreateDto.order().ID()));
        orderReport.setDescription(orderReportCreateDto.description());

        return orderReportMapper.toDto(orderReportRepository.save(orderReport));
    }

    @Transactional
    public void delete(Long orderReportId) {
        if (orderReportRepository.existsById(orderReportId)) {
            orderReportRepository.deleteById(orderReportId);
        } else {
            throw new IllegalArgumentException("Order Report with ID " + orderReportId + " does not exist");
        }
    }

    @Transactional
    public OrderReportDto update(Long orderReportId, OrderReportDto orderReportDto) {
        return orderReportRepository.findById(orderReportId)
                .map(orderReport -> {
                    orderReport.setOrder(orderRepository.getReferenceById(orderReportId));
                    orderReport.setDescription(orderReportDto.description());
                    return orderReportMapper.toDto(orderReportRepository.save(orderReport));
                })
                .orElseThrow(() -> new IllegalArgumentException("Order Report with ID " + orderReportId + " does not exist"));
    }

    @Transactional
    public Page<OrderReportDto> getAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return orderReportRepository.findAll(pageable)
                .map(orderReportMapper::toDto);
    }

    @Transactional
    public OrderReportDto getById(Long orderReportId) {
        return orderReportRepository.findById(orderReportId)
                .map(orderReportMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Order report with ID " + orderReportId + " does not exist"));
    }
}
