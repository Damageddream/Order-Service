package com.example.order_service.service;


import com.example.order_service.dto.NewOrderDto;
import com.example.order_service.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto addOrder(NewOrderDto newOrderDto);
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(String uuid);
    OrderDto updateOrder(String uuid, NewOrderDto newOrderDto);
    void deleteOrder(String uuid);
}
