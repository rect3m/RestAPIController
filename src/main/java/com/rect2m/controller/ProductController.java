package com.rect2m.controller;

import com.rect2m.entity.Product;
import com.rect2m.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get products with pagination and filters by name and price")
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price,
            Pageable pageable) {
        Page<Product> products = productService.getProducts(name, price, pageable);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No products found");
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found"));
    }

    @PostMapping
    @Operation(summary = "Add a new product")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("POST method not allowed for modification");
        }
        Product createdProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping
    @Operation(summary = "Update product")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("PUT method not allowed for creation");
        }
        Optional<Product> existingProduct = productService.getProductById(product.getId());
        if (existingProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found for update");
        }
        Product updatedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}

