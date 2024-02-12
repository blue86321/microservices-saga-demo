package com.chunwei.choreography.order.controller;

import com.chunwei.choreography.order.service.OrderService;
import com.chunwei.protos.order.PlaceOrderRequest;
import com.chunwei.protos.order.PlaceOrderResponse;
import com.chunwei.protos.order.OrderGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class OrderGrpcController extends OrderGrpc.OrderImplBase {
    private final OrderService orderService;

    public OrderGrpcController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void placeOrder(PlaceOrderRequest request,
                            io.grpc.stub.StreamObserver<PlaceOrderResponse> responseObserver) {
        responseObserver.onNext(orderService.placeOrder(request));
        responseObserver.onCompleted();
    }
}
