package com.example.order_service.entity;

import com.example.order_service.dto.InvoiceItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String id;
    private String uuid;
    private String from;
    private String to;
    private List<InvoiceItemDto> products;
    private Double totalPrice;
    private String timeStamp;
}
