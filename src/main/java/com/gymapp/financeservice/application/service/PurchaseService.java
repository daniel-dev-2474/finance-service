package com.gymapp.financeservice.application.service;

import com.gymapp.common.exceptions.custom.BadRequestException;
import com.gymapp.common.exceptions.custom.ResourceNotFoundException;
import com.gymapp.financeservice.application.port.out.EventPublisherPort;
import com.gymapp.financeservice.application.usecase.CreatePurchaseUseCase;
import com.gymapp.financeservice.application.usecase.FindPurchaseUseCase;
import com.gymapp.financeservice.application.usecase.StripeService;
import com.gymapp.financeservice.domain.model.PurchaseProduct;
import com.gymapp.financeservice.domain.repository.ProductRepositoryPort;
import com.gymapp.financeservice.domain.repository.PurchaseRepositoryPort;
import com.gymapp.financeservice.infrastructure.adapter.client.UserClient;
import com.gymapp.financeservice.shared.dto.PurchaseResult;
import com.gymapp.financeservice.shared.enums.PaymentMethod;
import com.gymapp.financeservice.shared.enums.ProductType;
import com.gymapp.financeservice.shared.enums.PurchaseStatus;
import com.gymapp.financeservice.infrastructure.event.PurchaseCreatedEvent;
import com.gymapp.financeservice.infrastructure.kafka.mapper.PurchaseEventMapper;
import com.gymapp.financeservice.domain.model.Product;
import com.gymapp.financeservice.domain.model.Purchase;
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
public class PurchaseService implements CreatePurchaseUseCase, FindPurchaseUseCase {

  private final PurchaseRepositoryPort purchaseRepository;
  private final UserClient userClient;
  private final ProductRepositoryPort productRepository;
  private final EventPublisherPort eventProducer;
  private final StripeService stripeService;

  @Override
  public PurchaseResult execute(Purchase purchase) {
    log.info("[purchase-service] Validating userId={} before purchase creation", purchase.getUserId());

    userClient.getUserById(purchase.getUserId());
    log.info("[purchase-service] userId={} is valid", purchase.getUserId());


    List<String> productIds = purchase.getItems().stream()
        .map(PurchaseProduct::getProductId)
        .toList();
    List<Product> products = productRepository.findAllByIds(productIds);
    if (products.size() != productIds.size()) {
      throw new ResourceNotFoundException("One or more productIds are invalid.");
    }

    if (!products.stream().allMatch(Product::isActive)) {
      throw new BadRequestException("One or more selected products are inactive.");
    }
    log.info("[purchase-service] All {} products are valid and active", products.size());

    purchase.setStatus(purchase.getPaymentMethod() == PaymentMethod.CASH
        ? PurchaseStatus.PAID
        : PurchaseStatus.PENDING);
    purchase.setCreatedAt(LocalDateTime.now());

    if (purchase.getStatus() == PurchaseStatus.PAID) {
      purchase.setPaidAt(LocalDateTime.now());
    }

    log.info("purchase: {}", purchase);
    Purchase saved = purchaseRepository.save(purchase);

    log.info("purchase: {}", saved);
    boolean hasRelevantProducts = saved.getItems().stream()
        .anyMatch(item -> item.getProductType() == ProductType.MEMBERSHIP
            || item.getProductType() == ProductType.PASS);
    log.info("has relevantProducts: {}", hasRelevantProducts);
    log.info("saved.getStatus() == PurchaseStatus.PAID: {}", saved.getStatus() == PurchaseStatus.PAID);
    if (saved.getStatus() == PurchaseStatus.PAID && hasRelevantProducts) {
      PurchaseCreatedEvent event = PurchaseEventMapper.toEvent(saved);
      eventProducer.emitPurchaseCreated(event);
    }

    String clientSecret = null;
    if (saved.getPaymentMethod() == PaymentMethod.CARD) {
      clientSecret = stripeService.createPaymentIntent(saved);
    }

    return new PurchaseResult(saved, clientSecret);
  }

  /**
  public Purchase findById(String id) {
    return purchaseRepository.find;
  }**/

  @Override
  public List<Purchase> findByUserId(String userId) {
    return purchaseRepository.findByUserId(userId);
  }
}
