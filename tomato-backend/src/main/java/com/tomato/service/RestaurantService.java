package com.tomato.service;

import com.tomato.dto.PagedResponse;
import com.tomato.dto.RestaurantDTO;

public interface RestaurantService {
    PagedResponse<RestaurantDTO> getAll(String city, String cuisine, int page, int size);
    RestaurantDTO getById(Long id);
    RestaurantDTO create(RestaurantDTO dto);
    RestaurantDTO update(Long id, RestaurantDTO dto);
    void delete(Long id);
}
