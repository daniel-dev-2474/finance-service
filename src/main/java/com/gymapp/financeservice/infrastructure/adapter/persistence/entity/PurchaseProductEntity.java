package com.gymapp.financeservice.infrastructure.adapter.persistence.entity;

import com.gymapp.financeservice.shared.enums.ProductType;
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
public class PurchaseProductEntity {
  /**
   * ID of the product at the moment of purchase.
   */
  private String productId;

  /**
   * Name of the product at the moment of purchase.
   */
  private String name;

  private ProductType productType;

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
