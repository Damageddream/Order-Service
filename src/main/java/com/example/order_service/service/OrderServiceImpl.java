package com.example.order_service.service;

import com.example.order_service.dto.NewOrderDto;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.mapper.OrderMapper;
import com.example.order_service.entity.Order;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto addOrder(NewOrderDto newOrderDto) {
        Order order = orderMapper.toOrderFromNewOrderDto(newOrderDto);
        orderRepository.save(order);

        return orderMapper.toOrderDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(String uuid) {
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(()-> new OrderNotFoundException("Order not found"));

        return orderMapper.toOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto updateOrder(String uuid, NewOrderDto newOrderDto) {
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        orderMapper.updateOrderFromDto(newOrderDto, order);
        orderRepository.save(order);

        return orderMapper.toOrderDto(order);
    }

    @Override
    @Transactional
    public void deleteOrder(String uuid) {
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(()-> new OrderNotFoundException("Order not found"));
        orderRepository.delete(order);
    }
}
