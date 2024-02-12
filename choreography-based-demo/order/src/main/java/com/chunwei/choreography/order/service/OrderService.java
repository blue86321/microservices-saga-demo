package com.chunwei.choreography.order.service;

import com.chunwei.choreography.common.dto.CheckInventoryEventRequestDto;
import com.chunwei.choreography.common.dto.CheckInventoryEventResponseDto;
import com.chunwei.choreography.common.kafka.TopicName;
import com.chunwei.protos.order.PlaceOrderRequest;
import com.chunwei.protos.order.PlaceOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Slf4j
public class OrderService {
    private final KafkaTemplate<Integer, CheckInventoryEventRequestDto> kafkaTemplate;
    private final ConcurrentMap<String, BlockingQueue<CheckInventoryEventResponseDto>> correlationIdToQueue =
        new ConcurrentHashMap<>();

    public OrderService(KafkaTemplate<Integer, CheckInventoryEventRequestDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public PlaceOrderResponse placeOrder(PlaceOrderRequest request) {
        String productId = request.getProductId();
        int quantity = request.getQuantity();

        log.info("##### [Order]: Send CheckInventoryRequest to Message Queue");
        String correlationId = UUID.randomUUID().toString();
        // Create a blocking queue for the response associated with the correlation ID
        BlockingQueue<CheckInventoryEventResponseDto> responseQueue = new LinkedBlockingQueue<>();
        correlationIdToQueue.put(correlationId, responseQueue);
        kafkaTemplate.send(TopicName.TOPIC_CHECK_INVENTORY_REQUEST, CheckInventoryEventRequestDto.builder()
            .correlationId(correlationId)
            .productId(productId)
            .quantity(quantity)
            .build());

        // Wait for the response
        try {
            CheckInventoryEventResponseDto responseDto = responseQueue.take();

            log.info("##### [Order]: Creating Order");

            // YOUR CREATE LOGICS...

            log.info("##### [Order]: PlaceOrder Done and Return");
            return PlaceOrderResponse.newBuilder()
                .setOrderId("DUMMY_ORDER_ID")
                .setSuccess(responseDto.isSufficient())
                .build();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Error waiting for CheckInventoryResponse", e);
            return PlaceOrderResponse.newBuilder()
                .setSuccess(false)
                .build();
        } finally {
            correlationIdToQueue.remove(correlationId);
        }
    }

    @KafkaListener(topics = TopicName.TOPIC_CHECK_INVENTORY_RESPONSE, groupId = "group1")
    public void handleCheckInventoryResponse(CheckInventoryEventResponseDto responseDto) {
        log.info("##### [Order]: Receive CheckInventoryResponse from Message Queue");

        String correlationId = responseDto.getCorrelationId();
        BlockingQueue<CheckInventoryEventResponseDto> responseQueue = correlationIdToQueue.get(correlationId);

        if (responseQueue == null) {
            log.info("No response queue found for correlation ID: {}", correlationId);
            return;
        }

        responseQueue.add(responseDto);
    }
}
