package com.gymapp.financeservice.application.service;

import com.gymapp.common.exceptions.custom.ResourceNotFoundException;
import com.gymapp.financeservice.application.port.out.EventPublisherPort;
import com.gymapp.financeservice.application.usecase.UpdatePurchaseStatusUseCase;
import com.gymapp.financeservice.domain.model.Purchase;
import com.gymapp.financeservice.domain.repository.PurchaseRepositoryPort;
import com.gymapp.financeservice.infrastructure.event.PurchaseCreatedEvent;
import com.gymapp.financeservice.infrastructure.kafka.mapper.PurchaseEventMapper;
import com.gymapp.financeservice.shared.enums.ProductType;
import com.gymapp.financeservice.shared.enums.PurchaseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdatePurchaseStatusService implements UpdatePurchaseStatusUseCase {

  private final PurchaseRepositoryPort purchaseRepository;

  private final EventPublisherPort eventPublisher;
  @Override
  public void markAsPaid(String purchaseId) {
    Purchase purchase = purchaseRepository.findById(purchaseId)
        .orElseThrow(() -> new ResourceNotFoundException("Purchase not found"));

    purchase.setStatus(PurchaseStatus.PAID);
    purchase.setPaidAt(LocalDateTime.now());
    purchaseRepository.save(purchase);

    boolean hasRelevantProducts = purchase.getItems().stream()
        .anyMatch(item -> item.getProductType() == ProductType.MEMBERSHIP
        || item.getProductType() == ProductType.PASS);

    if(hasRelevantProducts) {
      PurchaseCreatedEvent event = PurchaseEventMapper.toEvent(purchase);
      eventPublisher.emitPurchaseCreated(event);
    }

  }
}
