package com.example.order_service.dto.mapper;

import com.example.order_service.dto.InvoiceDto;
import com.example.order_service.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface InvoiceToOrderMapper {
    @Mappings(
            {
                    @Mapping(target = "from", source = "from"),
                    @Mapping(target = "to", source = "to"),
                    @Mapping(target = "items", source = "products"),
                    @Mapping(target = "logo", constant = ""),
                    @Mapping(target = "number", constant = "1"),
                    @Mapping(target = "date", expression = "java(java.time.LocalDate.now().toString())"),
                    @Mapping(target = "due_date", expression = "java(java.time.LocalDate.now().plusDays(30).toString())"),
                    @Mapping(target = "notes", constant = "Thank for your business"),
                    @Mapping(target = "terms", constant = "Please pay by the due date.")
            }
    )
    InvoiceDto orderToInvoice(OrderDto order);
}
