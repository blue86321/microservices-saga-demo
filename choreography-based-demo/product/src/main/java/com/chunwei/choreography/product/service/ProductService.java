package com.chunwei.choreography.product.service;

import com.chunwei.choreography.common.dto.CheckInventoryEventRequestDto;
import com.chunwei.choreography.common.dto.CheckInventoryEventResponseDto;
import com.chunwei.choreography.common.kafka.TopicName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

    private final KafkaTemplate<Integer, CheckInventoryEventResponseDto> kafkaTemplate;

    public ProductService(KafkaTemplate<Integer, CheckInventoryEventResponseDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = TopicName.TOPIC_CHECK_INVENTORY_REQUEST, groupId = "group1")
    public void handleCheckInventoryRequest(CheckInventoryEventRequestDto responseDto) {
        log.info("##### [Product]: Receive CheckInventoryRequest from Message Queue");
        log.info("##### [Product]: Checking Inventory");

        // YOUR CHECK LOGIC...

        log.info("##### [Product]: Send CheckInventoryResponse to Message Queue");
        kafkaTemplate.send(TopicName.TOPIC_CHECK_INVENTORY_RESPONSE, CheckInventoryEventResponseDto.builder()
            .correlationId(responseDto.getCorrelationId())
            .sufficient(true)
            .build());
    }
}
