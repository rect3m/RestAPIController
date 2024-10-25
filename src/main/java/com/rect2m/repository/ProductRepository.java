package com.rect2m.repository;

import com.rect2m.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContaining(String name, Pageable pageable);
    Page<Product> findByPrice(Double price, Pageable pageable);
    Page<Product> findByNameContainingAndPrice(String name, Double price, Pageable pageable);
}
