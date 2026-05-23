package com.tomato.repository;

import com.tomato.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE " +
           "(:city IS NULL OR LOWER(r.city) = LOWER(:city)) AND " +
           "(:cuisine IS NULL OR LOWER(r.cuisine) LIKE LOWER(CONCAT('%', :cuisine, '%')))")
    Page<Restaurant> findByFilters(@Param("city") String city,
                                   @Param("cuisine") String cuisine,
                                   Pageable pageable);
}
