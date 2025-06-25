package com.gymapp.financeservice.application.usecase;

import com.gymapp.financeservice.domain.model.Purchase;

import java.util.List;

public interface FindPurchaseUseCase {
//  Purchase findById(String id);

  List<Purchase> findByUserId(String userId);
}