package com.tomato.service.impl;

import com.tomato.dto.MenuItemDTO;
import com.tomato.exception.ResourceNotFoundException;
import com.tomato.model.MenuItem;
import com.tomato.model.Restaurant;
import com.tomato.repository.MenuItemRepository;
import com.tomato.repository.RestaurantRepository;
import com.tomato.service.MenuService;
import com.tomato.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public List<MenuItemDTO> getByRestaurantId(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + restaurantId);
        }
        return menuItemRepository.findByRestaurantId(restaurantId).stream()
                .map(MapperUtil::toMenuItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MenuItemDTO create(Long restaurantId, MenuItemDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        MenuItem item = MenuItem.builder()
                .restaurant(restaurant)
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .imageUrl(dto.getImageUrl())
                .build();

        return MapperUtil.toMenuItemDTO(menuItemRepository.save(item));
    }

    @Override
    @Transactional
    public MenuItemDTO update(Long itemId, MenuItemDTO dto) {
        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + itemId));

        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setCategory(dto.getCategory());
        item.setImageUrl(dto.getImageUrl());

        return MapperUtil.toMenuItemDTO(menuItemRepository.save(item));
    }

    @Override
    @Transactional
    public void delete(Long itemId) {
        if (!menuItemRepository.existsById(itemId)) {
            throw new ResourceNotFoundException("Menu item not found with id: " + itemId);
        }
        menuItemRepository.deleteById(itemId);
    }
}
