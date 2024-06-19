package com.example.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private String from;
    private String to;
    private String logo;
    private Integer number;
    private String date;
    private String due_date;
    private List<InvoiceItemDto> items;
    private String notes;
    private String terms;
}
