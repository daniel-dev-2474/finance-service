package com.gymapp.financeservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a product and quantity in a purchase request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchasedItemRequest {

  @NotBlank
  private String productId;

  @Min(1)
  private int quantity;
}
