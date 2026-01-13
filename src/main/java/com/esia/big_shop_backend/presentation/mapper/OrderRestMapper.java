package com.esia.big_shop_backend.presentation.mapper;

import com.esia.big_shop_backend.application.usecase.order.command.CreateOrderCommand;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.entity.OrderItem;
import com.esia.big_shop_backend.presentation.dto.request.order.CreateOrderRequest;
import com.esia.big_shop_backend.presentation.dto.response.address.AddressResponse;
import com.esia.big_shop_backend.presentation.dto.response.order.OrderItemResponse;
import com.esia.big_shop_backend.presentation.dto.response.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderRestMapper {

    public CreateOrderCommand toCommand(Long userId, CreateOrderRequest request) {
        List<CreateOrderCommand.OrderItemDto> items = request.getItems().stream()
                .map(item -> new CreateOrderCommand.OrderItemDto(
                        item.getProductId(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        return new CreateOrderCommand(
                userId,
                items,
                request.getShippingFullName(),
                request.getShippingPhone(),
                request.getShippingAddress()
        );
    }

    public OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems() != null
                ? order.getItems().stream()
                        .map(this::toItemResponse)
                        .collect(Collectors.toList())
                : List.of();

        AddressResponse shippingAddress = null;
        if (order.getShippingInfo() != null) {
            shippingAddress = AddressResponse.builder()
                    .fullName(order.getShippingInfo().getFullName())
                    .phone(order.getShippingInfo().getPhoneNumber() != null
                            ? order.getShippingInfo().getPhoneNumber().getValue() : null)
                    .street(order.getShippingInfo().getAddressText())
                    .build();
        }

        return new OrderResponse(
                order.getId() != null ? order.getId().getValue() : null,
                order.getOrderNumber(),
                order.getUserId() != null ? order.getUserId().getValue() : null,
                order.getStatus() != null ? order.getStatus().name() : null,
                order.getTotalItems(),
                order.getTotalAmount(),
                shippingAddress,
                order.getCreatedAt(),
                itemResponses
        );
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getProductId() != null ? item.getProductId().getValue() : null,
                item.getProductName(),
                item.getQuantity(),
                item.getUnitPrice() != null ? item.getUnitPrice().getAmount() : 0.0,
                null // imageUrl - peut être ajouté plus tard
        );
    }
}
