package com.chunwei.choreography.gateway.controller;

import com.chunwei.choreography.common.dto.PlaceOrderRequestDto;
import com.chunwei.choreography.gateway.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GatewayController {

    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody PlaceOrderRequestDto placeOrderRequestDto) {
        return gatewayService.placeOrder(placeOrderRequestDto);
    }
}
