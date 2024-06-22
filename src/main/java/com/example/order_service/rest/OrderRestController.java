package com.example.order_service.rest;

import com.example.order_service.dto.NewOrderDto;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.service.InvoiceService;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class OrderRestController {
    private final InvoiceService invoiceService;
    private final OrderService orderService;

    @PostMapping("/{uuid}/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<String>> createInvoice(@PathVariable String uuid) {
        return invoiceService.generateInvServ(uuid)
                .map(url -> ResponseEntity.status(HttpStatus.CREATED).body(url))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto addOrder(@RequestBody NewOrderDto newOrder) {
        return orderService.addOrder(newOrder);
    }

    @GetMapping
    public List<OrderDto> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{uuid}")
    public OrderDto getOrder(@PathVariable String uuid) {
        return orderService.getOrderById(uuid);
    }

    @PutMapping("/{uuid}")
    public OrderDto updateOrder(@PathVariable String uuid, @RequestBody NewOrderDto order) {
        return orderService.updateOrder(uuid, order);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String uuid) {
        orderService.deleteOrder(uuid);
    }
}
