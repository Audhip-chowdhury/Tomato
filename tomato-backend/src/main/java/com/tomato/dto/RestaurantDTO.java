package com.tomato.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Cuisine is required")
    private String cuisine;

    @NotBlank(message = "City is required")
    private String city;

    @NotNull(message = "Rating is required")
    private Double rating;

    private String imageUrl;

    @NotNull(message = "isOpen is required")
    private Boolean isOpen;
}
