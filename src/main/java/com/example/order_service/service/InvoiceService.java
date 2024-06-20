package com.example.order_service.service;

import com.example.order_service.dto.InvoiceDto;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.mapper.InvoiceToOrderMapper;
import com.example.order_service.dto.mapper.OrderMapper;
import com.example.order_service.entity.Order;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final WebClient webClient;
    private final OrderMapper orderMapper;
    private final InvoiceToOrderMapper invoiceToOrderMapper;
    private final OrderRepository orderRepository;

    public void generateInvServ(String uuid){
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(()->new RuntimeException("Order not found"));
        OrderDto orderDto = orderMapper.toOrderDto(order);
        InvoiceDto invoiceBody = invoiceToOrderMapper.orderToInvoice(orderDto);
        generateInvoice(invoiceBody).subscribe();
    }

    private Mono<Void> generateInvoice(InvoiceDto invoiceBody) {

        return webClient.post()
                .bodyValue(invoiceBody)
                .retrieve()
                .bodyToMono(byte[].class)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(response -> {
                    Path path = Paths.get("src/main/resources/static/invoice.pdf");
                    try {
                        Files.write(path, response);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .then();
    }
}
