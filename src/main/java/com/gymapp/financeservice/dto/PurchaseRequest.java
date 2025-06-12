package com.gymapp.financeservice.dto;

import com.gymapp.financeservice.constant.Currency;
import com.gymapp.financeservice.constant.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for creating a new purchase.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequest {

  @NotBlank
  private String userId;

  @NotNull
  @Valid
  private List<PurchasedItemRequest> items;


  @NotNull
  private Currency currency;

  @NotNull
  private PaymentMethod paymentMethod;
}
