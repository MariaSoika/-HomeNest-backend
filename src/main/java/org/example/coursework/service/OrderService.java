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

    @Transactional
    public void deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new IllegalArgumentException("Order with ID " + orderId + " does not exist");
        }
    }

    @Transactional
    public OrderDto updateOrder(Long orderId, OrderCreateDto orderCreateDto){
        return orderRepository.findById(orderId)
                .map(existingOrder -> {
                    existingOrder.setUser(userRepository.getReferenceById(orderCreateDto.userID()));
                    existingOrder.setApartment(apartmentRepository.getReferenceById(orderCreateDto.apartmentID()));
                    existingOrder.setOrderDate(LocalDate.now()); 

                    return orderMapper.toDto(orderRepository.save(existingOrder));
                })
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " does not exist"));

    }
}
