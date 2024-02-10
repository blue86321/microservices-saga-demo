package com.chunwei.orchestration.orchestrator.service;

import com.chunwei.orchestration.common.dto.PlaceOrderRequestDto;
import com.chunwei.protos.order.CreateOrderRequest;
import com.chunwei.protos.order.CreateOrderResponse;
import com.chunwei.protos.order.OrderGrpc;
import com.chunwei.protos.product.CheckInventoryRequest;
import com.chunwei.protos.product.ProductGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrchestratorService {
    @GrpcClient("order")
    OrderGrpc.OrderBlockingStub orderBlockingStub;

    @GrpcClient("product")
    ProductGrpc.ProductBlockingStub productBlockingStub;

    public ResponseEntity<String> placeOrder(PlaceOrderRequestDto placeOrderRequestDto) {
        String productId = placeOrderRequestDto.getProductId();
        String userId = placeOrderRequestDto.getUserId();
        int quantity = placeOrderRequestDto.getQuantity();
        int totalPrice = placeOrderRequestDto.getTotalPrice();

        // Check Inventory
        log.info("##### [Orchestrator]: PlaceOrder - Check Inventory with Product Service");
        boolean sufficientStock = productBlockingStub
            .checkInventory(CheckInventoryRequest.newBuilder()
                .setProductId(productId)
                .setQuantity(quantity)
                .build())
            .getSufficientStock();
        log.info("##### [Orchestrator]: PlaceOrder - Check Inventory Done");

        if (!sufficientStock) {
            return ResponseEntity.badRequest().body("Out of stock");
        }

        // Create Order
        log.info("##### [Orchestrator]: PlaceOrder - Create Order with Order Service");
        CreateOrderResponse createOrderResponse = orderBlockingStub.createOrder(CreateOrderRequest.newBuilder()
            .setUserId(userId)
            .setProductId(productId)
            .setQuantity(quantity)
            .setTotalPrice(totalPrice)
            .build());
        log.info("##### [Orchestrator]: PlaceOrder - Create Order Done");

        log.info("##### [Orchestrator]: PlaceOrder - Receive Result and Return");
        if (!createOrderResponse.getSuccess()) {
            return ResponseEntity.badRequest().body("Create order error.");
        }

        return ResponseEntity.ok("Order created, order ID: " + createOrderResponse.getOrderId());
    }
}
