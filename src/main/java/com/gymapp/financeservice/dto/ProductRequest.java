package com.gymapp.financeservice.dto;

import com.gymapp.financeservice.constant.Currency;
import com.gymapp.financeservice.constant.ProductType;
import jakarta.validation.Valid;
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
