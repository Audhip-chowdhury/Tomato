package com.tomato.controller;

import com.tomato.dto.ApiResponse;
import com.tomato.dto.MenuItemDTO;
import com.tomato.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/api/restaurants/{id}/menu")
    public ResponseEntity<ApiResponse<List<MenuItemDTO>>> getMenu(@PathVariable Long id) {
        List<MenuItemDTO> menu = menuService.getByRestaurantId(id);
        return ResponseEntity.ok(ApiResponse.success(menu, "Menu retrieved successfully"));
    }

    @PostMapping("/api/restaurants/{id}/menu")
    public ResponseEntity<ApiResponse<MenuItemDTO>> createMenuItem(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemDTO dto) {
        MenuItemDTO created = menuService.create(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Menu item created successfully"));
    }

    @PutMapping("/api/menu/{itemId}")
    public ResponseEntity<ApiResponse<MenuItemDTO>> updateMenuItem(
            @PathVariable Long itemId,
            @Valid @RequestBody MenuItemDTO dto) {
        MenuItemDTO updated = menuService.update(itemId, dto);
        return ResponseEntity.ok(ApiResponse.success(updated, "Menu item updated successfully"));
    }

    @DeleteMapping("/api/menu/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteMenuItem(@PathVariable Long itemId) {
        menuService.delete(itemId);
        return ResponseEntity.ok(ApiResponse.success(null, "Menu item deleted successfully"));
    }
}
