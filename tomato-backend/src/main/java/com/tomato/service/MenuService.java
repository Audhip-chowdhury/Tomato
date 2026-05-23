package com.tomato.service;

import com.tomato.dto.MenuItemDTO;

import java.util.List;

public interface MenuService {
    List<MenuItemDTO> getByRestaurantId(Long restaurantId);
    MenuItemDTO create(Long restaurantId, MenuItemDTO dto);
    MenuItemDTO update(Long itemId, MenuItemDTO dto);
    void delete(Long itemId);
}
