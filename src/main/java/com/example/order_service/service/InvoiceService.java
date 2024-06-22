package com.example.order_service.service;

import com.example.order_service.dto.InvoiceDto;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.mapper.InvoiceToOrderMapper;
import com.example.order_service.dto.mapper.OrderMapper;
import com.example.order_service.entity.Order;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final WebClient webClient;
    private final OrderMapper orderMapper;
    private final InvoiceToOrderMapper invoiceToOrderMapper;
    private final OrderRepository orderRepository;
    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.url}")
    private String s3Url;

    public Mono<String> generateInvServ(String uuid){
        return Mono.fromCallable(() -> orderRepository.findByUuid(uuid)
                        .orElseThrow(() -> new RuntimeException("Order not found")))
                .flatMap(order -> {
                    OrderDto orderDto = orderMapper.toOrderDto(order);
                    InvoiceDto invoiceBody = invoiceToOrderMapper.orderToInvoice(orderDto);
                    return generateInvoice(invoiceBody);
                });
    }

    private Mono<String> generateInvoice(InvoiceDto invoiceBody) {

        return webClient.post()
                .bodyValue(invoiceBody)
                .retrieve()
                .bodyToMono(byte[].class)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(response -> {
                    String key = "invoices/invoice-"+System.currentTimeMillis()+".pdf";
                    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build();
                    s3Client.putObject(putObjectRequest, RequestBody.fromBytes(response));
                    String url = s3Url+key;
                    return Mono.just(url);
                });
    }
}
