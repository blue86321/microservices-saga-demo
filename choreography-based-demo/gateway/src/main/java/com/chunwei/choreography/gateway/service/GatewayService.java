package com.chunwei.choreography.gateway.service;

import com.chunwei.choreography.common.dto.PlaceOrderRequestDto;
import com.chunwei.protos.order.CreateOrderRequest;
import com.chunwei.protos.order.CreateOrderResponse;
import com.chunwei.protos.order.OrderGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
public class GatewayService {
    @GrpcClient("order")
    OrderGrpc.OrderBlockingStub orderBlockingStub;

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody PlaceOrderRequestDto placeOrderRequestDto) {
        log.info("##### [Gateway]: PlaceOrder - Go to Order Service");
        CreateOrderResponse createOrderResponse = orderBlockingStub.createOrder(CreateOrderRequest.newBuilder()
            .setUserId(placeOrderRequestDto.getUserId())
            .setProductId(placeOrderRequestDto.getProductId())
            .setQuantity(placeOrderRequestDto.getQuantity())
            .setTotalPrice(placeOrderRequestDto.getTotalPrice())
            .build());

        log.info("##### [Gateway]: PlaceOrder - Receive Result and Return");
        if (!createOrderResponse.getSuccess()) {
            return ResponseEntity.badRequest().body("Create order error.");
        }
        return ResponseEntity.ok("Order created, order ID: " + createOrderResponse.getOrderId());
    }
}
