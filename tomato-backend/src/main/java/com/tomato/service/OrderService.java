package com.tomato.service;

import com.tomato.dto.CreateOrderRequest;
import com.tomato.dto.OrderDTO;
import com.tomato.model.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(String email, CreateOrderRequest request);
    List<OrderDTO> getMyOrders(String email);
    OrderDTO getOrderByIdForUser(String email, Long orderId);
    OrderDTO getOrderTrackingForUser(String email, Long orderId);
    OrderDTO updateOrderStatus(Long orderId, OrderStatus status);
}
