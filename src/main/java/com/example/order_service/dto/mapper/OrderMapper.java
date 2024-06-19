package com.example.order_service.dto.mapper;

import com.example.order_service.dto.OrderDto;
import com.example.order_service.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toOrderDto(Order order);
    Order toOrder(OrderDto orderDto);
}
