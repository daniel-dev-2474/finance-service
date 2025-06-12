package com.gymapp.financeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO representing a purchased product within a purchase response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchasedItemResponse {

  private String productId;
  private String name;
  private int quantity;
  private BigDecimal unitPrice;
  private BigDecimal subtotal;
}
