package com.example.order_service.dto.mapper;

import com.example.order_service.dto.NewOrderDto;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toOrderDto(Order order);
    Order toOrder(OrderDto orderDto);
    @Mappings({
            @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID().toString())")
    })
    Order toOrderFromNewOrderDto(NewOrderDto newOrderDto);
    void updateOrderFromDto(NewOrderDto newOrderDto, @MappingTarget Order order);
}
