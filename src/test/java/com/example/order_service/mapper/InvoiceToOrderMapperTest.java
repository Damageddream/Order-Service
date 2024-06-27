package com.example.order_service.mapper;

import com.example.order_service.dto.InvoiceDto;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.mapper.InvoiceToOrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.order_service.dto.InvoiceDto;
import com.example.order_service.dto.InvoiceItemDto;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.mapper.InvoiceToOrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class InvoiceToOrderMapperTest {

    private InvoiceToOrderMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(InvoiceToOrderMapper.class);
    }

    @Test
    void testOrderToInvoice() {
        // Arrange
        OrderDto order = createSampleOrderDto();

        // Act
        InvoiceDto invoice = mapper.orderToInvoice(order);

        // Assert
        assertNotNull(invoice);
        assertEquals(order.getFrom(), invoice.getFrom());
        assertEquals(order.getTo(), invoice.getTo());
        assertEquals(order.getProducts().size(), invoice.getItems().size());
        assertEquals("", invoice.getLogo());
        assertNotNull(invoice.getNumber());
        assertTrue(invoice.getNumber() >= 0 && invoice.getNumber() < 10000);
        assertEquals(LocalDate.now().toString(), invoice.getDate());
        assertEquals(LocalDate.now().plusDays(30).toString(), invoice.getDue_date());
        assertEquals("Thank for your business", invoice.getNotes());
        assertEquals("Please pay by the due date.", invoice.getTerms());

        // Check items
        for (int i = 0; i < order.getProducts().size(); i++) {
            assertEquals(order.getProducts().get(i), invoice.getItems().get(i));
        }
    }

    private OrderDto createSampleOrderDto() {
        List<InvoiceItemDto> items = Arrays.asList(
                InvoiceItemDto.builder().name("Item 1").quantity(2).unit_cost(10.0).build(),
                InvoiceItemDto.builder().name("Item 2").quantity(1).unit_cost(20.0).build()
        );

        return OrderDto.builder()
                .from("Seller")
                .to("Buyer")
                .products(items)
                .build();
    }
}
