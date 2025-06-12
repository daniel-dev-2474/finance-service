package com.gymapp.financeservice.service.impl;

import com.gymapp.common.exceptions.custom.ResourceNotFoundException;
import com.gymapp.financeservice.dto.ProductRequest;
import com.gymapp.financeservice.dto.ProductResponse;
import com.gymapp.financeservice.mapper.ProductMapper;
import com.gymapp.financeservice.model.Product;
import com.gymapp.financeservice.repository.ProductRepository;
import com.gymapp.financeservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of product-related operations.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository repository;

  @Override
  public ProductResponse create(ProductRequest request) {
    Product product = ProductMapper.toEntity(request);
    return ProductMapper.toResponse(repository.save(product));
  }

  @Override
  public ProductResponse getById(String id) {
    Product product = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    return ProductMapper.toResponse(product);
  }

  @Override
  public List<ProductResponse> getAllActive() {
    return repository.findByActiveTrue()
        .stream()
        .map(ProductMapper::toResponse)
        .toList();
  }

  @Override
  public ProductResponse update(String id, ProductRequest request) {
    Product existing = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    existing.setName(request.getName());
    existing.setDescription(request.getDescription());
    existing.setPrice(request.getPrice());
    existing.setType(request.getType());
    existing.setCurrency(request.getCurrency());
    return ProductMapper.toResponse(repository.save(existing));
  }

  @Override
  public void delete(String id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Product not found with ID: " + id);
    }
    repository.deleteById(id);
  }
}
