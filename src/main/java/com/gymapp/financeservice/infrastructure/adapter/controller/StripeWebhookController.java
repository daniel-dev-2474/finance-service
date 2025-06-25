package com.gymapp.financeservice.infrastructure.adapter.controller;

import com.gymapp.financeservice.application.usecase.UpdatePurchaseStatusUseCase;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Scanner;

@RestController
@RequestMapping("/api/webhook/stripe")
@RequiredArgsConstructor
public class StripeWebhookController {

  private final UpdatePurchaseStatusUseCase updatePurchaseStatusUseCase;

  @Value("${stripe.webhook.secret}")
  private String endpointSecret;

  @PostMapping
  public ResponseEntity<String> handleStripeEvent(HttpServletRequest request) {
    String payload;
    String sigHeader = request.getHeader("Stripe-Signature");
    try (Scanner scanner = new Scanner(request.getInputStream(),"UTF-8").useDelimiter("\\A")) {
      payload = scanner.hasNext() ? scanner.next() : "";
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payload");
    }
    Event event;


    try {
      event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
    } catch (SignatureVerificationException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
    }
    if("payment_intent.succeeded".equals(event.getType())) {
      PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
          .getObject().orElse(null);
      if(intent != null) {
        String purchaseId = intent.getMetadata().get("purchaseId");
        updatePurchaseStatusUseCase.markAsPaid(purchaseId);
      }
    }
    return ResponseEntity.ok("Received");
  }

}
