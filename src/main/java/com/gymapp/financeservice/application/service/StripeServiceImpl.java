package com.gymapp.financeservice.application.service;

import com.gymapp.financeservice.application.usecase.StripeService;
import com.gymapp.financeservice.domain.model.Purchase;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {

  @Value("${stripe.secret-key}")
  private String stripeApiKey;

  @PostConstruct
  public void init() {
    Stripe.apiKey = stripeApiKey;
  }

  @Override
  public String createPaymentIntent(Purchase purchase) {
    try {
      Map<String, Object> params = new HashMap<>();
      params.put("amount", purchase.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue());
      params.put("currency", purchase.getCurrency().name().toLowerCase());
      params.put("metadata",Map.of("purchaseId", purchase.getId()));

      PaymentIntent intent = PaymentIntent.create(params);
      return intent.getClientSecret();
    } catch (StripeException e) {
      throw new RuntimeException("Stripe payment intent creation failed", e);
    }
  }
}
