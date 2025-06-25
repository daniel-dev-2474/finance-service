package com.gymapp.financeservice.infrastructure.adapter.persistence.entity;

import com.gymapp.financeservice.shared.enums.Currency;
import com.gymapp.financeservice.shared.enums.PaymentMethod;
import com.gymapp.financeservice.shared.enums.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a purchase made by a user. Stores the full purchase details
 * as a snapshot in the "purchases" collection.
 */
@Document(collection = "purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseEntity {

  /**
   * Unique identifier of the purchase.
   */
  @Id
  private String id;

  /**
   * ID of the user who made the purchase.
   */
  private String userId;

  /**
   * List of purchased products with quantity and pricing.
   */
  private List<PurchaseProductEntity> items;

  /**
   * Total amount paid for the purchase.
   */
  private BigDecimal totalAmount;

  /**
   * ISO 4217 currency code (e.g. MXN, USD).
   */
  private Currency currency;

  /**
   * Method used to pay (e.g. CREDIT_CARD, CASH, PAYPAL).
   */
  private PaymentMethod paymentMethod;


  /** Current status of the purchase (PENDING, PAID, FAILED, EXPIRED). */
  private PurchaseStatus status;

  /** External payment ID returned by Stripe, MercadoPago, etc. */
  private String externalPaymentId;

  /** Payment reference for OXXO/SPEI-type methods. */
  private String paymentReference;

  /** Expiration time for the payment (e.g. 48h after creation). */
  private LocalDateTime expiresAt;

  /** Timestamp when the purchase was created. */
  private LocalDateTime createdAt;

  /** Timestamp when the purchase was successfully paid. */
  private LocalDateTime paidAt;
}

