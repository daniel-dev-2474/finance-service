package com.gymapp.financeservice.model;

import com.gymapp.financeservice.constant.Currency;
import com.gymapp.financeservice.constant.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Represents a sellable product in the gym system, such as memberships,
 * individual passes, or items.
 */
@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

  /**
   * Unique identifier of the product.
   */
  @Id
  private String id;

  /**
   * Human-readable name of the product.
   */
  private String name;

  /**
   * Description providing more context about the product.
   */
  private String description;

  /**
   * Price of a single unit of the product.
   */
  private BigDecimal price;

  /**
   * Indicates if the product is currently available for sale.
   */
  private boolean active;

  /**
   * Logical classification of the product (e.g. MEMBERSHIP, PASS, ITEM).
   */
  private ProductType type;

  /**
   * ISO 4217 currency code (e.g. MXN, USD).
   */
  private Currency currency;

  private ProductDetails details;

}
