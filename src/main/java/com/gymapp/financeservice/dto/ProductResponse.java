package com.gymapp.financeservice.dto;

import com.gymapp.financeservice.constant.Currency;
import com.gymapp.financeservice.constant.ProductType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for exposing product information to clients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

  private String id;
  private String name;
  private String description;
  private BigDecimal price;
  private boolean active;
  private ProductType type;
  private Currency currency;
  private ProductDetailsDTO details;
}