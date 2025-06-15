package com.gymapp.financeservice.service.impl;

import com.gymapp.common.exceptions.custom.BadRequestException;
import com.gymapp.common.exceptions.custom.ResourceNotFoundException;
import com.gymapp.financeservice.client.UserClient;
import com.gymapp.financeservice.constant.PaymentMethod;
import com.gymapp.financeservice.constant.ProductType;
import com.gymapp.financeservice.constant.PurchaseStatus;
import com.gymapp.financeservice.dto.PurchaseRequest;
import com.gymapp.financeservice.dto.PurchaseResponse;
import com.gymapp.financeservice.dto.PurchasedItemRequest;
import com.gymapp.financeservice.event.PurchaseCreatedEvent;
import com.gymapp.financeservice.kafka.PurchaseEventProducer;
import com.gymapp.financeservice.mapper.PurchaseEventMapper;
import com.gymapp.financeservice.mapper.PurchaseMapper;
import com.gymapp.financeservice.model.Product;
import com.gymapp.financeservice.model.Purchase;
import com.gymapp.financeservice.repository.ProductRepository;
import com.gymapp.financeservice.repository.PurchaseRepository;
import com.gymapp.financeservice.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of purchase-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

  private final PurchaseRepository purchaseRepo;
  private final ProductRepository productRepo;
  private final UserClient userClient;
  private final PurchaseEventProducer eventProducer;

  @Override
  public PurchaseResponse create(PurchaseRequest request) {
    log.info("[purchase-service] Validating userId={} before purchase creation", request.getUserId());

    userClient.getUserById(request.getUserId());
    log.info("[purchase-service] userId={} is valid", request.getUserId());


    List<String> productIds = request.getItems().stream()
        .map(PurchasedItemRequest::getProductId)
        .toList();
    List<Product> products = productRepo.findAllById(productIds);

    if (products.size() != productIds.size()) {
      throw new ResourceNotFoundException("One or more productIds are invalid.");
    }

    if (!products.stream().allMatch(Product::isActive)) {
      throw new BadRequestException("One or more selected products are inactive.");
    }
    log.info("[purchase-service] All {} products are valid and active", products.size());
    Purchase purchase = PurchaseMapper.toEntity(request, products);

    // Establecer valores por defecto
    purchase.setStatus(request.getPaymentMethod() == PaymentMethod.CASH
        ? PurchaseStatus.PAID
        : PurchaseStatus.PENDING);
    purchase.setCreatedAt(LocalDateTime.now());

    if (purchase.getStatus() == PurchaseStatus.PAID) {
      purchase.setPaidAt(LocalDateTime.now());
    }

    Purchase saved = purchaseRepo.save(purchase);

    boolean hasRelevantProducts = saved.getItems().stream()
        .anyMatch(item -> item.getProductType() == ProductType.MEMBERSHIP
            || item.getProductType() == ProductType.PASS);

    if (saved.getStatus() == PurchaseStatus.PAID && hasRelevantProducts) {
      PurchaseCreatedEvent event = PurchaseEventMapper.toEvent(saved);
      eventProducer.emitPurchaseCreated(event);
    }

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
