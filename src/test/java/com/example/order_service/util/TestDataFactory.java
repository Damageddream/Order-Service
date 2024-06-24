package com.example.order_service.util;

import com.example.order_service.dto.InvoiceDto;
import com.example.order_service.dto.InvoiceItemDto;
import com.example.order_service.dto.NewOrderDto;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.entity.Order;

import java.util.List;

public class TestDataFactory {

    public static InvoiceItemDto createInvoiceItemDto(String name, Integer quantity, Double unitCost) {
        return InvoiceItemDto.builder()
                .name(name)
                .quantity(quantity)
                .unit_cost(unitCost)
                .build();
    }

    public static Order createOrder() {
        InvoiceItemDto invoiceItemDto1 = createInvoiceItemDto("product1", 1, 1000.00);
        InvoiceItemDto invoiceItemDto2 = createInvoiceItemDto("product2", 2, 2000.00);
        return Order.builder()
                .id("1")
                .uuid("123")
                .from("from")
                .to("to")
                .products(List.of(invoiceItemDto1, invoiceItemDto2))
                .totalPrice(5000.00)
                .timeStamp("2021-08-01")
                .build();
    }

    public static OrderDto createOrderDto() {
        InvoiceItemDto invoiceItemDto1 = createInvoiceItemDto("product1", 1, 1000.00);
        InvoiceItemDto invoiceItemDto2 = createInvoiceItemDto("product2", 2, 2000.00);
        return OrderDto.builder()
                .uuid("123")
                .from("from")
                .to("to")
                .products(List.of(invoiceItemDto1, invoiceItemDto2))
                .totalPrice(5000.00)
                .timeStamp("2021-08-01")
                .build();
    }

    public static NewOrderDto createNewOrderDto() {
        InvoiceItemDto invoiceItemDto1 = createInvoiceItemDto("product1", 1, 1000.00);
        InvoiceItemDto invoiceItemDto2 = createInvoiceItemDto("product2", 2, 2000.00);
        return NewOrderDto.builder()
                .from("from")
                .to("to")
                .products(List.of(invoiceItemDto1, invoiceItemDto2))
                .totalPrice(5000.00)
                .timeStamp("2021-08-01")
                .build();
    }

    public static InvoiceDto createInvoiceDto() {
        InvoiceItemDto invoiceItemDto1 = createInvoiceItemDto("product1", 1, 1000.00);
        InvoiceItemDto invoiceItemDto2 = createInvoiceItemDto("product2", 2, 2000.00);
        return InvoiceDto.builder()
                .logo("")
                .from("from")
                .to("to")
                .items(List.of(invoiceItemDto1, invoiceItemDto2))
                .date("2021-08-01")
                .due_date("2021-08-31")
                .notes("notes")
                .terms("terms")
                .number(1)
                .build();
    }
}
