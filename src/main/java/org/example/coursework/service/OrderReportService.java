package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.OrderReportCreateDto;
import org.example.coursework.dto.OrderReportDto;
import org.example.coursework.entity.OrderReport;
import org.example.coursework.exception.OrderReportNotFoundException;
import org.example.coursework.mapper.OrderReportMapper;
import org.example.coursework.repository.OrderReportRepository;
import org.example.coursework.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class OrderReportService {

    private static final Logger logger = LoggerFactory.getLogger(OrderReportService.class);

    private final OrderReportMapper orderReportMapper;
    private OrderRepository orderRepository;
    private OrderReportRepository orderReportRepository;

    @CacheEvict(value = "orderReports", allEntries = true)
    @Transactional
    public OrderReportDto create(OrderReportCreateDto orderReportCreateDto) {
        OrderReport orderReport = new OrderReport();
        orderReport.setOrder(orderRepository.getReferenceById(orderReportCreateDto.order().ID()));
        orderReport.setDescription(orderReportCreateDto.description());
        logger.info("Created orderReport with ID: {}", orderReport.getId());
        return orderReportMapper.toDto(orderReportRepository.save(orderReport));
    }

    @CacheEvict(value = "orderReports", key = "#orderReportId")
    @Transactional
    public void delete(Long orderReportId) throws OrderReportNotFoundException {
        if (orderReportRepository.existsById(orderReportId)) {
            orderReportRepository.deleteById(orderReportId);
            logger.info("Deleted orderReport with ID: {}", orderReportId);
        } else {
            logger.error("Order Report with ID" + orderReportId + "does not exist");
            throw new OrderReportNotFoundException("Order Report with ID " + orderReportId + " does not exist");
        }
    }

    @CacheEvict(value = "orderReports", key = "#orderReportId")
    @Transactional
    public OrderReportDto update(Long orderReportId, OrderReportDto orderReportDto) throws OrderReportNotFoundException {
        logger.info("Updating orderReport with ID: {}", orderReportId);
        return orderReportRepository.findById(orderReportId)
                .map(orderReport -> {
                    orderReport.setOrder(orderRepository.getReferenceById(orderReportId));
                    orderReport.setDescription(orderReportDto.description());
                    return orderReportMapper.toDto(orderReportRepository.save(orderReport));
                })
                .orElseThrow(() -> {
                    logger.error("Order Report with ID: {} does not exist", orderReportId);
                    return new OrderReportNotFoundException("Order Report with ID " + orderReportId + " does not exist");
                });
    }

    @Cacheable(value = "orderReports", key = "#page + '-' + #size")
    @Transactional
    public Page<OrderReportDto> getAll(int page, int size) {
        logger.info("Fetching all orderReports - Page: {}, Size: {}", page, size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return orderReportRepository.findAll(pageable)
                .map(orderReportMapper::toDto);
    }

    @Cacheable(value = "orderReports", key = "#orderReportId")
    @Transactional
    public OrderReportDto getById(Long orderReportId) throws OrderReportNotFoundException {
        return orderReportRepository.findById(orderReportId)
                .map(orderReportMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("Order Report with ID: {} does not exist", orderReportId);
                    return new OrderReportNotFoundException("Order Report with ID " + orderReportId + " does not exist");
                });
    }
}
