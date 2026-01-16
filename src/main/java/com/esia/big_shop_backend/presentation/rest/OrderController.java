package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.order.*;
import com.esia.big_shop_backend.application.usecase.order.command.*;
import com.esia.big_shop_backend.application.usecase.order.query.GetAllOrdersQuery;
import com.esia.big_shop_backend.application.usecase.order.query.GetOrderQuery;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import com.esia.big_shop_backend.presentation.dto.request.order.CreateOrderRequest;
import com.esia.big_shop_backend.presentation.dto.response.order.OrderResponse;
import com.esia.big_shop_backend.presentation.mapper.OrderRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order management APIs")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final OrderRestMapper mapper;

    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderResponse> createOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid CreateOrderRequest request) {
        Long userId = getUserIdFromUserDetails(userDetails);
        CreateOrderCommand command = mapper.toCommand(userId, request);
        Order order = createOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(order));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all orders (Admin)")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(Pageable pageable) {
        GetAllOrdersQuery query = new GetAllOrdersQuery(pageable.getPageNumber(), pageable.getPageSize());

        Object result = getAllOrdersUseCase.execute(query);
        if (result instanceof Page) {
             Page<Order> orders = (Page<Order>) result;
             return ResponseEntity.ok(orders.map(mapper::toResponse));
        } else {
             List<Order> orders = (List<Order>) result;
             List<OrderResponse> responses = orders.stream().map(mapper::toResponse).collect(Collectors.toList());
             return ResponseEntity.ok(new PageImpl<>(responses, pageable, responses.size()));
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel an order")
    public ResponseEntity<Void> cancelOrder(@PathVariable OrderId id) {
        CancelOrderCommand command = new CancelOrderCommand(id);
        cancelOrderUseCase.execute(command.getOrderId());
        return ResponseEntity.noContent().build();
    }

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        if (userDetails instanceof com.esia.big_shop_backend.infrastrucute.sercutity.CustomUserDetails) {
            return ((com.esia.big_shop_backend.infrastrucute.sercutity.CustomUserDetails) userDetails).getUserId();
        }
        throw new IllegalStateException("UserDetails is not an instance of CustomUserDetails");
    }
}
