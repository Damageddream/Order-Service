package com.example.order_service.rest;

import com.example.order_service.dto.NewOrderDto;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.service.InvoiceService;
import com.example.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invoice created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @PostMapping("/{uuid}/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<String>> createInvoice(@PathVariable String uuid) {
        return invoiceService.generateInvServ(uuid)
                .map(url -> ResponseEntity.status(HttpStatus.CREATED).body(url))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(summary = "Add new order")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class))})
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto addOrder(@RequestBody NewOrderDto newOrder) {
        return orderService.addOrder(newOrder);
    }

    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found orders",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class))})
    })

    @GetMapping
    public List<OrderDto> getOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Get order by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class))}),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @GetMapping("/{uuid}")
    public OrderDto getOrder(@PathVariable String uuid) {
        return orderService.getOrderById(uuid);
    }

    @Operation(summary = "Update order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDto.class))}),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @PutMapping("/{uuid}")
    public OrderDto updateOrder(@PathVariable String uuid, @RequestBody NewOrderDto order) {
        return orderService.updateOrder(uuid, order);
    }

    @Operation(summary = "Delete order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String uuid) {
        orderService.deleteOrder(uuid);
    }
}
