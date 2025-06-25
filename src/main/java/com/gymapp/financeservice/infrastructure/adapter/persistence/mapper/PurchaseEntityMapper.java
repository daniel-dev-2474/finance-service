package com.gymapp.financeservice.infrastructure.adapter.persistence.mapper;

import com.gymapp.financeservice.domain.model.Purchase;
import com.gymapp.financeservice.domain.model.PurchaseProduct;
import com.gymapp.financeservice.infrastructure.adapter.persistence.entity.PurchaseEntity;
import com.gymapp.financeservice.infrastructure.adapter.persistence.entity.PurchaseProductEntity;

import java.util.List;

public class PurchaseEntityMapper {

  public static Purchase toDomain(PurchaseEntity entity) {
    List<PurchaseProduct> products = entity.getItems().stream()
        .map(p -> PurchaseProduct.builder()
            .productId(p.getProductId())
            .name(p.getName())
            .unitPrice(p.getUnitPrice())
            .productType(p.getProductType())
            .quantity(p.getQuantity())
            .subtotal(p.getSubtotal())
            .build())
        .toList();

    return Purchase.builder()
        .id(entity.getId())
        .userId(entity.getUserId())
        .items(products)
        .totalAmount(entity.getTotalAmount())
        .currency(entity.getCurrency())
        .paymentMethod(entity.getPaymentMethod())
        .status(entity.getStatus())
        .externalPaymentId(entity.getExternalPaymentId())
        .paymentReference(entity.getPaymentReference())
        .expiresAt(entity.getExpiresAt())
        .createdAt(entity.getCreatedAt())
        .paidAt(entity.getPaidAt())
        .build();
  }

  public static PurchaseEntity toEntity(Purchase domain) {
    List<PurchaseProductEntity> items = domain.getItems().stream()
        .map(p -> PurchaseProductEntity.builder()
            .productId(p.getProductId())
            .name(p.getName())
            .productType(p.getProductType())
            .unitPrice(p.getUnitPrice())
            .quantity(p.getQuantity())
            .subtotal(p.getSubtotal())
            .build())
        .toList();

    return PurchaseEntity.builder()
        .id(domain.getId())
        .userId(domain.getUserId())
        .items(items)
        .totalAmount(domain.getTotalAmount())
        .currency(domain.getCurrency())
        .paymentMethod(domain.getPaymentMethod())
        .status(domain.getStatus())
        .externalPaymentId(domain.getExternalPaymentId())
        .paymentReference(domain.getPaymentReference())
        .expiresAt(domain.getExpiresAt())
        .createdAt(domain.getCreatedAt())
        .paidAt(domain.getPaidAt())
        .build();
  }
}
