package com.tomato.service.impl;

import com.tomato.dto.CreateOrderRequest;
import com.tomato.dto.OrderDTO;
import com.tomato.exception.ResourceNotFoundException;
import com.tomato.model.*;
import com.tomato.repository.CartRepository;
import com.tomato.repository.OrderRepository;
import com.tomato.repository.UserRepository;
import com.tomato.service.OrderService;
import com.tomato.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderDTO placeOrder(String email, CreateOrderRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Restaurant restaurant = cart.getItems().get(0).getMenuItem().getRestaurant();
        boolean mixedRestaurants = cart.getItems().stream()
                .anyMatch(item -> !item.getMenuItem().getRestaurant().getId().equals(restaurant.getId()));
        if (mixedRestaurants) {
            throw new IllegalArgumentException("Cart contains items from multiple restaurants");
        }

        BigDecimal total = cart.getItems().stream()
                .map(item -> item.getMenuItem().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDateTime now = LocalDateTime.now();
        Order order = Order.builder()
                .user(user)
                .restaurant(restaurant)
                .status(OrderStatus.PLACED)
                .statusVersion(1)
                .deliveryAddress(request.getDeliveryAddress())
                .total(total)
                .createdAt(now)
                .updatedAt(now)
                .build();

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> OrderItem.builder()
                        .order(order)
                        .menuItem(cartItem.getMenuItem())
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getMenuItem().getPrice())
                        .build())
                .toList();
        order.setItems(orderItems);

        Order saved = orderRepository.save(order);
        cart.getItems().clear();
        cartRepository.save(cart);

        return MapperUtil.toOrderDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getMyOrders(String email) {
        return orderRepository.findByUserEmailOrderByCreatedAtDesc(email).stream()
                .map(MapperUtil::toOrderDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrderByIdForUser(String email, Long orderId) {
        Order order = orderRepository.findByIdAndUserEmail(orderId, email)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return MapperUtil.toOrderDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrderTrackingForUser(String email, Long orderId) {
        return getOrderByIdForUser(email, orderId);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() != status) {
            order.setStatus(status);
            order.setStatusVersion(order.getStatusVersion() + 1);
            order.setUpdatedAt(LocalDateTime.now());
        }

        return MapperUtil.toOrderDTO(orderRepository.save(order));
    }
}
