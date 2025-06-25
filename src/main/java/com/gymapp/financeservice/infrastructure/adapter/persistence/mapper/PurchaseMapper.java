package com.gymapp.financeservice.infrastructure.adapter.persistence.mapper;

import com.gymapp.financeservice.shared.dto.PurchaseRequest;
import com.gymapp.financeservice.shared.dto.PurchaseResponse;
import com.gymapp.financeservice.shared.dto.PurchasedItemResponse;
import com.gymapp.financeservice.domain.model.Product;
import com.gymapp.financeservice.domain.model.Purchase;
import com.gymapp.financeservice.domain.model.PurchaseProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mapper class responsible for converting between Purchase entities and DTOs.
 */
public final class PurchaseMapper {

  /**
   * Converts a purchase request and list of products into a Purchase entity.
   *
   * @param request the purchase request containing user ID and item list.
   * @param products the list of products to be matched with item requests.
   * @return the populated Purchase entity.
   */
   public static Purchase toDomain(PurchaseRequest request, List<Product> products) {
     Map<String, Product> productMap = products.stream()
         .collect(Collectors.toMap(Product::getId, p -> p));

     List<PurchaseProduct> items = request.getItems().stream()
         .map(itemRequest -> {
           Product product = productMap.get(itemRequest.getProductId());
           return PurchaseProduct.builder()
               .productId(product.getId())
               .name(product.getName())
               .productType(product.getType())
               .unitPrice(product.getPrice())
               .quantity(itemRequest.getQuantity())
               .subtotal(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())))
               .build();
         }).collect(Collectors.toList());

     return Purchase.builder()
         .id(UUID.randomUUID().toString())
         .userId(request.getUserId())
         .paymentMethod(request.getPaymentMethod())
         .currency(request.getCurrency())
         .items(items)
         .totalAmount(items.stream()
             .map(PurchaseProduct::getSubtotal)
             .reduce(BigDecimal::add)
             .orElseThrow())
         .createdAt(LocalDateTime.now())
         .build();
   }

  /**
   * Converts a Purchase entity into a response DTO.
   *
   * @param entity the purchase entity to convert.
   * @return the response DTO with item details and totals.
   */
  public static PurchaseResponse toResponse(Purchase entity) {
    List<PurchasedItemResponse> itemResponses = entity.getItems().stream()
        .map(item -> PurchasedItemResponse.builder()
            .productId(item.getProductId())
            .name(item.getName())
            .unitPrice(item.getUnitPrice())
            .quantity(item.getQuantity())
            .subtotal(item.getSubtotal())

            .build())
        .toList();

    return PurchaseResponse.builder()
        .id(entity.getId())
        .userId(entity.getUserId())
        .paymentMethod(entity.getPaymentMethod())
        .currency(entity.getCurrency())
        .items(itemResponses)
        .totalAmount(entity.getTotalAmount())
        .createdAt(entity.getCreatedAt())
        .build();
  }
}
