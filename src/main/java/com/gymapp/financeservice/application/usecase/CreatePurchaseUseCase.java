package com.gymapp.financeservice.application.usecase;


import com.gymapp.financeservice.domain.model.Purchase;
import com.gymapp.financeservice.shared.dto.PurchaseResult;

public interface CreatePurchaseUseCase {
  PurchaseResult execute(Purchase purchase);
}
