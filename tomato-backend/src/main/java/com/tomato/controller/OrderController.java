package com.tomato.controller;

import com.tomato.dto.ApiResponse;
import com.tomato.dto.CreateOrderRequest;
import com.tomato.dto.OrderDTO;
import com.tomato.dto.OrderStatusUpdateRequest;
import com.tomato.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> placeOrder(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody CreateOrderRequest request) {
        OrderDTO order = orderService.placeOrder(email, request);
        return ResponseEntity.ok(ApiResponse.success(order, "Order placed successfully"));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getMyOrders(@AuthenticationPrincipal String email) {
        List<OrderDTO> orders = orderService.getMyOrders(email);
        return ResponseEntity.ok(ApiResponse.success(orders, "Orders retrieved successfully"));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrder(
            @AuthenticationPrincipal String email,
            @PathVariable Long orderId) {
        OrderDTO order = orderService.getOrderByIdForUser(email, orderId);
        return ResponseEntity.ok(ApiResponse.success(order, "Order retrieved successfully"));
    }

    @GetMapping("/{orderId}/tracking")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderTracking(
            @AuthenticationPrincipal String email,
            @PathVariable Long orderId) {
        OrderDTO order = orderService.getOrderTrackingForUser(email, orderId);
        return ResponseEntity.ok(ApiResponse.success(order, "Order tracking retrieved successfully"));
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateRequest request) {
        OrderDTO order = orderService.updateOrderStatus(orderId, request.getStatus());
        return ResponseEntity.ok(ApiResponse.success(order, "Order status updated"));
    }
}
