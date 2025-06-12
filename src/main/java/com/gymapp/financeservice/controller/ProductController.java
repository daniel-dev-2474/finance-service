package com.gymapp.financeservice.controller;


import com.gymapp.financeservice.dto.ProductRequest;
import com.gymapp.financeservice.dto.ProductResponse;
import com.gymapp.financeservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<ProductResponse> createProduct(
      @Valid @RequestBody ProductRequest request
  ) {
    ProductResponse response = productService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Retrieves all products currently available.
   *
   * @return list of product responses
   */
  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    List<ProductResponse> responses = productService.getAllActive();
    return ResponseEntity.ok(responses);
  }

  /**
   * Retrieves a product by its identifier.
   *
   * @param id the product ID
   * @return the product response
   */
  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
    ProductResponse response = productService.getById(id);
    return ResponseEntity.ok(response);
  }
}