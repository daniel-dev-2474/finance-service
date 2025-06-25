package com.gymapp.financeservice.infrastructure.adapter.controller;


import com.gymapp.financeservice.application.usecase.ProductService;
import com.gymapp.financeservice.domain.model.Product;
import com.gymapp.financeservice.infrastructure.adapter.persistence.mapper.ProductMapper;
import com.gymapp.financeservice.shared.dto.ProductRequest;
import com.gymapp.financeservice.shared.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for handling product-related operations.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  /**
   * Creates a new product in the system.
   *
   * @param request the product request payload
   * @return the created product response
   */
  @PostMapping
  public ResponseEntity<ProductResponse> create(
      @Valid @RequestBody ProductRequest request
  ) {
    Product product = ProductMapper.toDomain(request);
    Product saved = productService.create(product);
    ProductResponse response = ProductMapper.toResponse(saved);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Retrieves all products currently available.
   *
   * @return list of product responses
   */
  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    List<Product> products = productService.getAllActive();
    List<ProductResponse> responses = products.stream()
        .map(ProductMapper::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  /**
   * Retrieves a product by its identifier.
   *
   * @param id the product ID
   * @return the product response
   */
  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProductById(
      @PathVariable String id
  ) {
    Product product = productService.getById(id);
    ProductResponse response = ProductMapper.toResponse(product);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> update(
      @PathVariable String id, @RequestBody ProductRequest request
  ) {
    Product updatedProduct = ProductMapper.toDomain(request);
    Product saved = productService.update(id, updatedProduct);
    return ResponseEntity.ok(ProductMapper.toResponse(saved));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    productService.delete(id);
    return ResponseEntity.noContent().build();
  }
}