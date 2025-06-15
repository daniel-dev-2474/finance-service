package com.gymapp.financeservice.mapper;

import com.gymapp.financeservice.event.PurchaseCreatedEvent;
import com.gymapp.financeservice.event.PurchasedItem;
import com.gymapp.financeservice.model.Purchase;

public class PurchaseEventMapper {

  public static PurchaseCreatedEvent toEvent(Purchase purchase) {
    return PurchaseCreatedEvent.builder()
        .purchaseId(purchase.getId())
        .userId(purchase.getUserId())
        .currency(purchase.getCurrency().name())
        .paymentMethod(purchase.getPaymentMethod().name())
        .totalAmount(purchase.getTotalAmount())
        .items(purchase.getItems().stream()
            .map(item -> new PurchasedItem(
                item.getProductId(), item.getQuantity()))
            .toList())
        .build();
  }
}
