package com.rect2m.service;

import com.rect2m.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<Product> getProducts(String name, Double price, Pageable pageable);
    Optional<Product> getProductById(Long id);
    Product saveProduct(Product product);
    boolean deleteProduct(Long id);
}
