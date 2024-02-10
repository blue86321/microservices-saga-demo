package com.chunwei.orchestration.orders.controller;

import com.chunwei.orchestration.orders.service.OrderService;
import com.chunwei.protos.order.CreateOrderRequest;
import com.chunwei.protos.order.CreateOrderResponse;
import com.chunwei.protos.order.OrderGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class OrderGrpcController extends OrderGrpc.OrderImplBase {
    private final OrderService orderService;

    public OrderGrpcController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void createOrder(CreateOrderRequest request,
                            io.grpc.stub.StreamObserver<CreateOrderResponse> responseObserver) {
        responseObserver.onNext(orderService.createOrder(request));
        responseObserver.onCompleted();
    }
}
