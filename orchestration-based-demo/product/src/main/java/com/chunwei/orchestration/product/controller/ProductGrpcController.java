package com.chunwei.orchestration.product.controller;

import com.chunwei.orchestration.product.service.ProductService;
import com.chunwei.protos.product.CheckInventoryRequest;
import com.chunwei.protos.product.CheckInventoryResponse;
import com.chunwei.protos.product.ProductGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ProductGrpcController extends ProductGrpc.ProductImplBase {
    private final ProductService productService;

    public ProductGrpcController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void checkInventory(CheckInventoryRequest request, StreamObserver<CheckInventoryResponse> responseObserver) {
        responseObserver.onNext(productService.checkInventory(request));
        responseObserver.onCompleted();
    }
}
