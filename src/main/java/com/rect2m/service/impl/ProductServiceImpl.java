package com.rect2m.service.impl;

import com.rect2m.entity.Product;
import com.rect2m.repository.ProductRepository;
import com.rect2m.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> getProducts(String name, Double price, Pageable pageable) {
        if (name != null && price != null) {
            return productRepository.findByNameContainingAndPrice(name, price, pageable);
        } else if (name != null) {
            return productRepository.findByNameContaining(name, pageable);
        } else if (price != null) {
            return productRepository.findByPrice(price, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

