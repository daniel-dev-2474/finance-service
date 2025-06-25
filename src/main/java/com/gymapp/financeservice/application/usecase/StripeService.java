package com.gymapp.financeservice.application.usecase;

import com.gymapp.financeservice.domain.model.Purchase;

public interface StripeService {

  String createPaymentIntent(Purchase purchase);
}
