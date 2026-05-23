package com.tomato.util;

import com.tomato.dto.*;
import com.tomato.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public final class MapperUtil {

    private MapperUtil() {}

    public static UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .phone(user.getPhone())
                .build();
    }

    public static AuthResponse toAuthResponse(User user, String token) {
        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .phone(user.getPhone())
                .build();
    }

    public static RestaurantDTO toRestaurantDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .cuisine(restaurant.getCuisine())
                .city(restaurant.getCity())
                .rating(restaurant.getRating())
                .imageUrl(restaurant.getImageUrl())
                .isOpen(restaurant.getIsOpen())
                .build();
    }

    public static Restaurant toRestaurantEntity(RestaurantDTO dto) {
        return Restaurant.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cuisine(dto.getCuisine())
                .city(dto.getCity())
                .rating(dto.getRating())
                .imageUrl(dto.getImageUrl())
                .isOpen(dto.getIsOpen())
                .build();
    }

    public static MenuItemDTO toMenuItemDTO(MenuItem item) {
        return MenuItemDTO.builder()
                .id(item.getId())
                .restaurantId(item.getRestaurant().getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .category(item.getCategory())
                .imageUrl(item.getImageUrl())
                .build();
    }

    public static CartDTO toCartDTO(Cart cart) {
        List<CartItemDTO> items = cart.getItems().stream()
                .map(MapperUtil::toCartItemDTO)
                .collect(Collectors.toList());

        int totalItems = items.stream().mapToInt(CartItemDTO::getQuantity).sum();
        BigDecimal subtotal = items.stream()
                .map(CartItemDTO::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartDTO.builder()
                .id(cart.getId())
                .items(items)
                .totalItems(totalItems)
                .subtotal(subtotal)
                .build();
    }

    public static CartItemDTO toCartItemDTO(CartItem item) {
        BigDecimal lineTotal = item.getMenuItem().getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
        return CartItemDTO.builder()
                .id(item.getId())
                .menuItemId(item.getMenuItem().getId())
                .name(item.getMenuItem().getName())
                .description(item.getMenuItem().getDescription())
                .price(item.getMenuItem().getPrice())
                .imageUrl(item.getMenuItem().getImageUrl())
                .quantity(item.getQuantity())
                .lineTotal(lineTotal)
                .build();
    }

    public static OrderDTO toOrderDTO(Order order) {
        List<OrderItemDTO> items = order.getItems().stream()
                .map(MapperUtil::toOrderItemDTO)
                .toList();

        return OrderDTO.builder()
                .id(order.getId())
                .restaurantId(order.getRestaurant().getId())
                .restaurantName(order.getRestaurant().getName())
                .status(order.getStatus())
                .statusVersion(order.getStatusVersion())
                .total(order.getTotal())
                .deliveryAddress(order.getDeliveryAddress())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .items(items)
                .build();
    }

    public static OrderItemDTO toOrderItemDTO(OrderItem item) {
        BigDecimal lineTotal = item.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
        return OrderItemDTO.builder()
                .id(item.getId())
                .menuItemId(item.getMenuItem().getId())
                .name(item.getMenuItem().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .lineTotal(lineTotal)
                .build();
    }
}
