package com.gymapp.financeservice.infrastructure.adapter.controller;

import com.gymapp.common.exceptions.custom.ResourceNotFoundException;
import com.gymapp.financeservice.application.usecase.CreatePurchaseUseCase;
import com.gymapp.financeservice.application.usecase.FindPurchaseUseCase;
import com.gymapp.financeservice.application.usecase.ProductService;
import com.gymapp.financeservice.domain.model.Product;
import com.gymapp.financeservice.domain.model.Purchase;
import com.gymapp.financeservice.infrastructure.adapter.persistence.mapper.PurchaseMapper;
import com.gymapp.financeservice.shared.dto.PurchaseRequest;
import com.gymapp.financeservice.shared.dto.PurchaseResponse;
import com.gymapp.financeservice.shared.dto.PurchaseResult;
import com.gymapp.financeservice.shared.dto.PurchasedItemRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for handling purchase-related operations.
 */
@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

  private final CreatePurchaseUseCase createPurchaseUseCase;
  private final ProductService productService;
  private final FindPurchaseUseCase findPurchaseUseCase;

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
    List<String> productIds = request.getItems().stream()
        .map(PurchasedItemRequest::getProductId)
        .toList();
    List<Product> products = productService.getByIds(productIds);
    Purchase purchase = PurchaseMapper.toDomain(request, products);
    PurchaseResult result = createPurchaseUseCase.execute(purchase);
    PurchaseResponse response = PurchaseMapper.toResponse(result.purchase());
    response.setClientSecret(result.clientSecret());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<List<PurchaseResponse>> findByUserId(@PathVariable String userId) {
    List<Purchase> purchase = findPurchaseUseCase.findByUserId(userId);
    List<PurchaseResponse> responses = purchase
        .stream().map(PurchaseMapper::toResponse).toList();
    return ResponseEntity.ok(responses);
  }
}
