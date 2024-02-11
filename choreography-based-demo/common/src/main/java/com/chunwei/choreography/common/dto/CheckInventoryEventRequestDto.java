package com.chunwei.choreography.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInventoryEventRequestDto {
    private String correlationId;
    private String productId;
    private int quantity;
}
