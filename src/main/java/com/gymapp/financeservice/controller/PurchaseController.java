package com.gymapp.financeservice.controller;

import com.gymapp.financeservice.dto.PurchaseRequest;
import com.gymapp.financeservice.dto.PurchaseResponse;
import com.gymapp.financeservice.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling purchase-related operations.
 */
@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

  private final PurchaseService purchaseService;

  /**
   * Registers a new purchase for a given user and product list.
   *
   * @param request the request containing purchase details.
   * @return the purchase response with item and total details.
   */
  @PostMapping
  public ResponseEntity<PurchaseResponse> createPurchase(
      @Valid @RequestBody PurchaseRequest request
  ) {
    PurchaseResponse response = purchaseService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
