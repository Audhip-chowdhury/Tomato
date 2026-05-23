package com.tomato.repository;

import com.tomato.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"restaurant", "items", "items.menuItem"})
    Optional<Order> findByIdAndUserEmail(Long id, String email);

    @EntityGraph(attributePaths = {"restaurant", "items", "items.menuItem"})
    List<Order> findByUserEmailOrderByCreatedAtDesc(String email);
}
