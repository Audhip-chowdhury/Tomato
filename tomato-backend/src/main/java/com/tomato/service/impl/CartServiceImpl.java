package com.tomato.service.impl;

import com.tomato.dto.CartAddRequest;
import com.tomato.dto.CartDTO;
import com.tomato.dto.CartUpdateRequest;
import com.tomato.exception.ResourceNotFoundException;
import com.tomato.model.*;
import com.tomato.repository.*;
import com.tomato.service.CartService;
import com.tomato.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    @Transactional(readOnly = true)
    public CartDTO getCart(String email) {
        Cart cart = getOrCreateCart(email);
        return MapperUtil.toCartDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO addItem(String email, CartAddRequest request) {
        Cart cart = getOrCreateCart(email);
        MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + request.getMenuItemId()));

        CartItem existing = cartItemRepository.findByCartIdAndMenuItemId(cart.getId(), menuItem.getId())
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + request.getQuantity());
            cartItemRepository.save(existing);
        } else {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .menuItem(menuItem)
                    .quantity(request.getQuantity())
                    .build();
            cart.getItems().add(cartItem);
            cartRepository.save(cart);
        }

        return MapperUtil.toCartDTO(getOrCreateCart(email));
    }

    @Override
    @Transactional
    public CartDTO updateItem(String email, CartUpdateRequest request) {
        Cart cart = getOrCreateCart(email);

        CartItem cartItem = cartItemRepository.findByCartIdAndMenuItemId(cart.getId(), request.getMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

        return MapperUtil.toCartDTO(getOrCreateCart(email));
    }

    @Override
    @Transactional
    public CartDTO removeItem(String email, Long menuItemId) {
        Cart cart = getOrCreateCart(email);

        CartItem cartItem = cartItemRepository.findByCartIdAndMenuItemId(cart.getId(), menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        return MapperUtil.toCartDTO(getOrCreateCart(email));
    }

    @Override
    @Transactional
    public CartDTO clearCart(String email) {
        Cart cart = getOrCreateCart(email);
        cart.getItems().clear();
        cartRepository.save(cart);
        return MapperUtil.toCartDTO(cart);
    }

    private Cart getOrCreateCart(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(newCart);
                });
    }
}
