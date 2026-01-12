package com.esia.big_shop_backend.presentation.dto.response.order;

import com.esia.big_shop_backend.presentation.dto.response.address.AddressResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private Long userId;
    private String status;
    private int totalItems;
    private double totalAmount;
    private AddressResponse shippingAddress;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}
