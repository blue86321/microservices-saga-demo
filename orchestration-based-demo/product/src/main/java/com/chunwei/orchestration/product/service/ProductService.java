package com.chunwei.orchestration.product.service;

import com.chunwei.protos.product.CheckInventoryRequest;
import com.chunwei.protos.product.CheckInventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {
    public CheckInventoryResponse checkInventory(CheckInventoryRequest request) {
        log.info("##### [Product]: Checking Inventory");
        // YOUR CHECK LOGIC...
        return CheckInventoryResponse.newBuilder()
            .setSufficientStock(true)
            .build();
    }
}
