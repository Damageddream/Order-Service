package com.example.order_service.mapper;

import com.example.order_service.dto.mapper.OrderMapper;
import com.example.order_service.entity.Order;
import com.example.order_service.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderMapperTest {

    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        this.orderMapper = Mappers.getMapper(OrderMapper.class);
    }

    @Test
    void fromOrder_validOrder_OrderDTOReturned() {
        //given
        Order order = TestDataFactory.createOrder();

        //when
        var result = orderMapper.toOrderDto(order);

        //then
        assertNotNull(result);
        assertEquals("123", result.getUuid());
        assertEquals("from", result.getFrom());
        assertEquals("to", result.getTo());
        assertEquals(5000.00, result.getTotalPrice());
        assertEquals("2021-08-01", result.getTimeStamp());
        assertEquals(2, result.getProducts().size());
        assertEquals("product1", result.getProducts().get(0).getName());
        assertEquals(1, result.getProducts().get(0).getQuantity());
        assertEquals(1000.00, result.getProducts().get(0).getUnit_cost());
        assertEquals("product2", result.getProducts().get(1).getName());
        assertEquals(2, result.getProducts().get(1).getQuantity());
        assertEquals(2000.00, result.getProducts().get(1).getUnit_cost());
    }
}
