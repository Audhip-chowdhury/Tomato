package com.tomato.service.impl;

import com.tomato.dto.PagedResponse;
import com.tomato.dto.RestaurantDTO;
import com.tomato.exception.ResourceNotFoundException;
import com.tomato.model.Restaurant;
import com.tomato.repository.RestaurantRepository;
import com.tomato.service.RestaurantService;
import com.tomato.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public PagedResponse<RestaurantDTO> getAll(String city, String cuisine, int page, int size) {
        Page<Restaurant> result = restaurantRepository.findByFilters(
                city != null && !city.isBlank() ? city : null,
                cuisine != null && !cuisine.isBlank() ? cuisine : null,
                PageRequest.of(page, size)
        );

        List<RestaurantDTO> content = result.getContent().stream()
                .map(MapperUtil::toRestaurantDTO)
                .collect(Collectors.toList());

        return PagedResponse.<RestaurantDTO>builder()
                .content(content)
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    @Override
    public RestaurantDTO getById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        return MapperUtil.toRestaurantDTO(restaurant);
    }

    @Override
    @Transactional
    public RestaurantDTO create(RestaurantDTO dto) {
        Restaurant restaurant = MapperUtil.toRestaurantEntity(dto);
        restaurant.setId(null);
        return MapperUtil.toRestaurantDTO(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional
    public RestaurantDTO update(Long id, RestaurantDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));

        restaurant.setName(dto.getName());
        restaurant.setCuisine(dto.getCuisine());
        restaurant.setCity(dto.getCity());
        restaurant.setRating(dto.getRating());
        restaurant.setImageUrl(dto.getImageUrl());
        restaurant.setIsOpen(dto.getIsOpen());

        return MapperUtil.toRestaurantDTO(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
    }
}
