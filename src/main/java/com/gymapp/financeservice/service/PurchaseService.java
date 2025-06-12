package com.gymapp.financeservice.service;

import com.gymapp.financeservice.dto.PurchaseRequest;
import com.gymapp.financeservice.dto.PurchaseResponse;

import java.util.List;

/**
 * Service interface for managing purchases.
 */
public interface PurchaseService {

  PurchaseResponse create(PurchaseRequest request);

  PurchaseResponse getById(String id);

  List<PurchaseResponse> getByUserId(String userId);
}
