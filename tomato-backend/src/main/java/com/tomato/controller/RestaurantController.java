package com.tomato.controller;

import com.tomato.dto.ApiResponse;
import com.tomato.dto.PagedResponse;
import com.tomato.dto.RestaurantDTO;
import com.tomato.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<RestaurantDTO>>> getAll(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String cuisine,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<RestaurantDTO> result = restaurantService.getAll(city, cuisine, page, size);
        return ResponseEntity.ok(ApiResponse.success(result, "Restaurants retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantDTO>> getById(@PathVariable Long id) {
        RestaurantDTO restaurant = restaurantService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(restaurant, "Restaurant retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RestaurantDTO>> create(@Valid @RequestBody RestaurantDTO dto) {
        RestaurantDTO created = restaurantService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Restaurant created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantDTO dto) {
        RestaurantDTO updated = restaurantService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(updated, "Restaurant updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Restaurant deleted successfully"));
    }
}
