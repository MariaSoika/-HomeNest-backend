package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.OrderDto;
import org.example.coursework.dto.OrderCreateDto;
import org.example.coursework.entity.Order;
import org.example.coursework.exception.OrderNotFoundException;
import org.example.coursework.mapper.OrderMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.example.coursework.repository.OrderRepository;
import org.example.coursework.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;
    private final OrderRepository orderRepository;

    @CacheEvict(value = "orders", allEntries = true)
    @Transactional
    public OrderDto create(OrderCreateDto orderCreateDto) {
        Order order = new Order();
        order.setUser(userRepository.getReferenceById(orderCreateDto.apartmentID()));
        order.setApartment(apartmentRepository.getReferenceById(orderCreateDto.apartmentID()));
        order.setOrderDate(LocalDate.now());
        logger.info("Created orderReport with ID: {}", order.getID());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @CacheEvict(value = "orders", key = "#orderId")
    @Transactional
    public void delete(Long orderId) throws OrderNotFoundException {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            logger.info("Deleted orderReport with ID: {}", orderId);
        } else {
            logger.error("Order with ID" + orderId + "does not exist");
            throw new OrderNotFoundException("Order with ID " + orderId + " does not exist");
        }
    }

    @CacheEvict(value = "orders", key = "#orderId")
    @Transactional
    public OrderDto update(Long orderId, OrderDto orderDto) throws OrderNotFoundException {
        return orderRepository.findById(orderId)
                //check if id
                .map(existingOrder -> {
                    existingOrder.setUser(userRepository.getReferenceById(orderDto.userID()));
                    existingOrder.setApartment(apartmentRepository.getReferenceById(orderDto.apartmentID()));
                    existingOrder.setOrderDate(LocalDate.now());
                    logger.info("Updated orderReport with ID: {}", orderId);
                    return orderMapper.toDto(orderRepository.save(existingOrder));
                })
                .orElseThrow(() -> {
                    logger.error("Order with ID" + orderId + "does not exist");
                    return new OrderNotFoundException("Order with ID " + orderId + " does not exist");
                });

    }

    @Cacheable(value = "orders", key = "#page + '-' + #size")
    @Transactional
    public Page<OrderDto> getAll(int page, int size) {
        logger.info("Get all orders with page {} and size {}", page, size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
         return orderRepository.findAll(pageable)
                .map(orderMapper::toDto);
    }

    @Cacheable(value = "orders", key = "#orderId")
    @Transactional
    public OrderDto getById(Long orderId) throws OrderNotFoundException {
        logger.info("Get orderReport with ID: {}", orderId);
        return orderRepository.findById(orderId)
                .map(orderMapper::toDto)
                .orElseThrow(() -> {
                    logger.error("Order with ID" + orderId + "does not exist");
                    return new OrderNotFoundException("Order with ID " + orderId + " does not exist");
                });
    }
}
