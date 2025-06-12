package com.gymapp.financeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Subdocument representing a product purchased as part of a purchase.
 * Stored inside a Purchase document as a snapshot of the original product.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchasedProduct {
  /**
   * ID of the product at the moment of purchase.
   */
  private String productId;

  /**
   * Name of the product at the moment of purchase.
   */
  private String name;

  /**
   * Number of units purchased.
   */
  private int quantity;

  /**
   * Unit price at the time of purchase.
   */
  private BigDecimal unitPrice;

  /**
   * Subtotal = quantity * unitPrice.
   */
  private BigDecimal subtotal;
}
