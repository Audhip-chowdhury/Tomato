package com.tomato.service;

import com.tomato.dto.CartAddRequest;
import com.tomato.dto.CartDTO;
import com.tomato.dto.CartUpdateRequest;
import com.tomato.exception.ResourceNotFoundException;
import com.tomato.model.*;
import com.tomato.repository.*;
import com.tomato.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private Cart cart;
    private MenuItem menuItem;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).name("Test User").email("test@tomato.com").role(Role.USER).build();
        restaurant = Restaurant.builder().id(1L).name("Test Restaurant").cuisine("Indian").city("Mumbai").rating(4.5).isOpen(true).build();
        menuItem = MenuItem.builder().id(1L).restaurant(restaurant).name("Butter Chicken").price(new BigDecimal("349.00")).category("Main Course").build();
        cart = Cart.builder().id(1L).user(user).items(new ArrayList<>()).build();
    }

    @Test
    void addItem_newCart_createsCartAndAddsItem() {
        CartAddRequest request = CartAddRequest.builder().menuItemId(1L).quantity(2).build();

        when(userRepository.findByEmail("test@tomato.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(cartItemRepository.findByCartIdAndMenuItemId(1L, 1L)).thenReturn(Optional.empty());

        CartDTO result = cartService.addItem("test@tomato.com", request);

        assertNotNull(result);
        verify(cartRepository, atLeastOnce()).save(any(Cart.class));
    }

    @Test
    void addItem_existingItem_incrementsQuantity() {
        CartItem existingItem = CartItem.builder().id(1L).cart(cart).menuItem(menuItem).quantity(1).build();
        cart.getItems().add(existingItem);

        CartAddRequest request = CartAddRequest.builder().menuItemId(1L).quantity(2).build();

        when(userRepository.findByEmail("test@tomato.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(cartItemRepository.findByCartIdAndMenuItemId(1L, 1L)).thenReturn(Optional.of(existingItem));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(existingItem);

        CartDTO result = cartService.addItem("test@tomato.com", request);

        assertNotNull(result);
        verify(cartItemRepository).save(existingItem);
        assertEquals(3, existingItem.getQuantity());
    }

    @Test
    void removeItem_success() {
        CartItem cartItem = CartItem.builder().id(1L).cart(cart).menuItem(menuItem).quantity(2).build();
        cart.getItems().add(cartItem);

        when(userRepository.findByEmail("test@tomato.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndMenuItemId(1L, 1L)).thenReturn(Optional.of(cartItem));

        CartDTO result = cartService.removeItem("test@tomato.com", 1L);

        assertNotNull(result);
        verify(cartItemRepository).delete(cartItem);
    }

    @Test
    void clearCart_success() {
        CartItem cartItem = CartItem.builder().id(1L).cart(cart).menuItem(menuItem).quantity(2).build();
        cart.getItems().add(cartItem);

        when(userRepository.findByEmail("test@tomato.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartDTO result = cartService.clearCart("test@tomato.com");

        assertNotNull(result);
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void updateItem_notInCart_throwsException() {
        CartUpdateRequest request = CartUpdateRequest.builder().menuItemId(99L).quantity(1).build();

        when(userRepository.findByEmail("test@tomato.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndMenuItemId(1L, 99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.updateItem("test@tomato.com", request));
    }
}
