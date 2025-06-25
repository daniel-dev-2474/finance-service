package com.gymapp.financeservice.application.service;

import com.gymapp.common.exceptions.custom.ResourceNotFoundException;
import com.gymapp.financeservice.application.usecase.ProductService;
import com.gymapp.financeservice.domain.repository.ProductRepositoryPort;
import com.gymapp.financeservice.shared.dto.ProductRequest;
import com.gymapp.financeservice.shared.dto.ProductResponse;
import com.gymapp.financeservice.infrastructure.adapter.persistence.mapper.ProductMapper;
import com.gymapp.financeservice.domain.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of product-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

  private final ProductRepositoryPort productRepository;

  @Override
  public Product create(Product request) {
    return productRepository.save(request);
  }


  @Override
  public List<Product> getAllActive() {
    return productRepository.getAllActive();
  }

  @Override
  public Product getById(String id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
  }


  @Override
  public Product update(String id, Product request) {
    Product existing = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    existing.setName(request.getName());
    existing.setDescription(request.getDescription());
    existing.setPrice(request.getPrice());
    existing.setType(request.getType());
    existing.setCurrency(request.getCurrency());
    return productRepository.save(existing);
  }

  @Override
  public void delete(String id) {
    if (productRepository.findById(id).isEmpty()) {
      throw new ResourceNotFoundException("Product not found with ID: " + id);
    }
    productRepository.delete(id);
  }

  @Override
  public List<Product> getByIds(List<String> ids) {
    return productRepository.findAllByIds(ids);
  }
}
