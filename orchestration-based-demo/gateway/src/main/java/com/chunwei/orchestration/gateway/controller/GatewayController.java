package com.chunwei.orchestration.gateway.controller;

import com.chunwei.orchestration.common.dto.PlaceOrderRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class GatewayController {

    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody PlaceOrderRequestDto placeOrderRequestDto) {
        log.info("##### [Gateway]: PlaceOrder - Go to Orchestration");
        return new RestTemplate()
            .postForEntity("http://localhost:8081/order", placeOrderRequestDto, String.class);
    }
}
