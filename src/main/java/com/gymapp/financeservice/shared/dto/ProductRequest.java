package com.gymapp.financeservice.shared.dto;

import com.gymapp.financeservice.shared.enums.Currency;
import com.gymapp.financeservice.shared.enums.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for creating or updating a product.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotNull
  private BigDecimal price;
  private boolean active;

  @NotNull
  private ProductType type;

  @NotNull
  private Currency currency;

  @NotNull
  private ProductDetailsDTO details;
}
