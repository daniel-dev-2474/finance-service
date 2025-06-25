package com.gymapp.financeservice.shared.dto;

import com.gymapp.financeservice.domain.model.Purchase;

public record PurchaseResult(Purchase purchase, String clientSecret) {}
