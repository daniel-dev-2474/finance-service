package com.gymapp.financeservice.infrastructure.kafka.mapper;

import com.gymapp.financeservice.infrastructure.event.PurchaseCreatedEvent;
import com.gymapp.financeservice.infrastructure.event.PurchaseItem;
import com.gymapp.financeservice.domain.model.Purchase;

public class PurchaseEventMapper {

  public static PurchaseCreatedEvent toEvent(Purchase purchase) {
    return PurchaseCreatedEvent.builder()
        .purchaseId(purchase.getId())
        .userId(purchase.getUserId())
        .currency(purchase.getCurrency().name())
        .paymentMethod(purchase.getPaymentMethod().name())
        .totalAmount(purchase.getTotalAmount())
        .items(purchase.getItems().stream()
            .map(item -> new PurchaseItem(
                item.getProductId(), item.getQuantity()))
            .toList())
        .build();
  }
}
