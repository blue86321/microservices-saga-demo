package com.chunwei.orchestration.orders.service;

import com.chunwei.protos.order.CreateOrderRequest;
import com.chunwei.protos.order.CreateOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        log.info("##### [Order]: Creating Order");
        // YOUR CREATE LOGICS...
        return CreateOrderResponse.newBuilder()
            .setOrderId("DUMMY_ORDER_ID")
            .setSuccess(true)
            .build();
    }
}
