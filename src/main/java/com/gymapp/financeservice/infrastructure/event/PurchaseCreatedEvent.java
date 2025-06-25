package com.gymapp.financeservice.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseCreatedEvent {
  private String purchaseId;
  private String userId;
  private List<PurchaseItem> items;
  private BigDecimal totalAmount;
  private String currency;
  private String paymentMethod;


}
