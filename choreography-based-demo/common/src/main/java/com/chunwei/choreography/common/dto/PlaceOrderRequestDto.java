package com.chunwei.choreography.common.dto;

import lombok.Data;

@Data
public class PlaceOrderRequestDto {
    private String userId;
    private String productId;
    private int quantity;
    private int totalPrice;
}
