package com.gymapp.financeservice.service.impl;

import com.gymapp.common.exceptions.custom.ResourceNotFoundException;
import com.gymapp.financeservice.dto.PurchaseRequest;
import com.gymapp.financeservice.dto.PurchaseResponse;
import com.gymapp.financeservice.dto.PurchasedItemRequest;
import com.gymapp.financeservice.mapper.PurchaseMapper;
import com.gymapp.financeservice.model.Product;
import com.gymapp.financeservice.model.Purchase;
import com.gymapp.financeservice.repository.ProductRepository;
import com.gymapp.financeservice.repository.PurchaseRepository;
import com.gymapp.financeservice.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of purchase-related operations.
 */
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

  private final PurchaseRepository purchaseRepo;
  private final ProductRepository productRepo;

  @Override
  public PurchaseResponse create(PurchaseRequest request) {
    // Validar existencia de productos
    List<String> productIds = request.getItems().stream()
        .map(PurchasedItemRequest::getProductId)
        .toList();
    List<Product> products = productRepo.findAllById(productIds);

    if (products.size() != productIds.size()) {
      throw new ResourceNotFoundException("Some products do not exist.");
    }

    Purchase purchase = PurchaseMapper.toEntity(request, products);
    Purchase saved = purchaseRepo.save(purchase);

    return PurchaseMapper.toResponse(saved);
  }

  @Override
  public PurchaseResponse getById(String id) {
    Purchase purchase = purchaseRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with ID: " + id));
    return PurchaseMapper.toResponse(purchase);
  }

  @Override
  public List<PurchaseResponse> getByUserId(String userId) {
    return purchaseRepo.findByUserId(userId)
        .stream()
        .map(PurchaseMapper::toResponse)
        .toList();
  }
}
