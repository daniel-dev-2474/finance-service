package com.gymapp.financeservice.service;

import com.gymapp.financeservice.dto.ProductRequest;
import com.gymapp.financeservice.dto.ProductResponse;

import java.util.List;

/**
 * Service interface for managing product operations.
 */
public interface ProductService {

  ProductResponse create(ProductRequest request);

  ProductResponse getById(String id);

  List<ProductResponse> getAllActive();

  ProductResponse update(String id, ProductRequest request);

  void delete(String id);
}
