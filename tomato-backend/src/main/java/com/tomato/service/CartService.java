package com.tomato.service;

import com.tomato.dto.CartAddRequest;
import com.tomato.dto.CartDTO;
import com.tomato.dto.CartUpdateRequest;

public interface CartService {
    CartDTO getCart(String email);
    CartDTO addItem(String email, CartAddRequest request);
    CartDTO updateItem(String email, CartUpdateRequest request);
    CartDTO removeItem(String email, Long menuItemId);
    CartDTO clearCart(String email);
}
