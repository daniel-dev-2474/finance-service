package com.gymapp.financeservice.domain.repository;

import com.gymapp.financeservice.domain.model.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepositoryPort {

  Purchase save(Purchase purchase);

  Optional<Purchase> findById(String id);

  List<Purchase> findByUserId(String userId);
  
}
