package com.example.order_service.rest;

import com.example.order_service.dto.OrderDto;
import com.example.order_service.entity.Order;
import com.example.order_service.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class OrderRestController {
    private final InvoiceService invoiceService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createInvoice(@RequestBody Order order) {
        invoiceService.generateInvoice(order).subscribe();
    }
}
