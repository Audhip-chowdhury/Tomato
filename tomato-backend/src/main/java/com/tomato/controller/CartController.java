package com.tomato.controller;

import com.tomato.dto.*;
import com.tomato.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<CartDTO>> getCart(@AuthenticationPrincipal String email) {
        CartDTO cart = cartService.getCart(email);
        return ResponseEntity.ok(ApiResponse.success(cart, "Cart retrieved successfully"));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartDTO>> addItem(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody CartAddRequest request) {
        CartDTO cart = cartService.addItem(email, request);
        return ResponseEntity.ok(ApiResponse.success(cart, "Item added to cart"));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<CartDTO>> updateItem(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody CartUpdateRequest request) {
        CartDTO cart = cartService.updateItem(email, request);
        return ResponseEntity.ok(ApiResponse.success(cart, "Cart updated successfully"));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<ApiResponse<CartDTO>> removeItem(
            @AuthenticationPrincipal String email,
            @PathVariable Long itemId) {
        CartDTO cart = cartService.removeItem(email, itemId);
        return ResponseEntity.ok(ApiResponse.success(cart, "Item removed from cart"));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<CartDTO>> clearCart(@AuthenticationPrincipal String email) {
        CartDTO cart = cartService.clearCart(email);
        return ResponseEntity.ok(ApiResponse.success(cart, "Cart cleared successfully"));
    }
}
