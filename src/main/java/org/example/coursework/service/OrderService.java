package org.example.coursework.service;

import lombok.AllArgsConstructor;
import org.example.coursework.dto.OrderDto;
import org.example.coursework.dto.OrderCreateDto;
import org.example.coursework.entity.Order;
import org.example.coursework.mapper.OrderMapper;
import org.example.coursework.repository.ApartmentRepository;
import org.example.coursework.repository.OrderRepository;
import org.example.coursework.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderDto createOrder(OrderCreateDto orderCreateDto) {
        Order order = new Order();
        order.setUser(userRepository.getReferenceById(orderCreateDto.apartmentID()));
        order.setApartment(apartmentRepository.getReferenceById(orderCreateDto.apartmentID()));
        order.setOrderDate(LocalDate.now());

        return orderMapper.toDto(orderRepository.save(order));
    }
}
