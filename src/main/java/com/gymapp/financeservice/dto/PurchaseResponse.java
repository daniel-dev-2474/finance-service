package com.gymapp.financeservice.dto;

import com.gymapp.financeservice.constant.Currency;
import com.gymapp.financeservice.constant.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for exposing purchase information to clients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseResponse {

  private String id;
  private String userId;
  private List<PurchasedItemResponse> items;
  private BigDecimal totalAmount;

  private Currency currency;
  private PaymentMethod paymentMethod;
  private LocalDateTime createdAt;
}
