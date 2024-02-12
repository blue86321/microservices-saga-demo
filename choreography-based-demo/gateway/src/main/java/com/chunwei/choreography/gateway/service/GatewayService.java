package com.chunwei.choreography.gateway.service;

import com.chunwei.choreography.common.dto.PlaceOrderRequestDto;
import com.chunwei.protos.order.PlaceOrderRequest;
import com.chunwei.protos.order.PlaceOrderResponse;
import com.chunwei.protos.order.OrderGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GatewayService {
    @GrpcClient("order")
    OrderGrpc.OrderBlockingStub orderBlockingStub;

    public ResponseEntity<String> placeOrder(PlaceOrderRequestDto placeOrderRequestDto) {
        log.info("##### [Gateway]: PlaceOrder - Go to Order Service");
        PlaceOrderResponse createOrderResponse = orderBlockingStub.placeOrder(PlaceOrderRequest.newBuilder()
            .setUserId(placeOrderRequestDto.getUserId())
            .setProductId(placeOrderRequestDto.getProductId())
            .setQuantity(placeOrderRequestDto.getQuantity())
            .setTotalPrice(placeOrderRequestDto.getTotalPrice())
            .build());

        log.info("##### [Gateway]: PlaceOrder Done and Return");
        if (!createOrderResponse.getSuccess()) {
            return ResponseEntity.badRequest().body("Place order error.");
        }
        return ResponseEntity.ok("Order created, order ID: " + createOrderResponse.getOrderId());
    }
}
