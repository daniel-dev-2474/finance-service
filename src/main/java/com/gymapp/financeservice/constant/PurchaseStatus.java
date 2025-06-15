package com.gymapp.financeservice.constant;

/**
 * Enum representing the current status of a purchase.
 */
public enum PurchaseStatus {
  PENDING,   // Orden creada pero no pagada
  PAID,      // Pago confirmado
  FAILED,    // Pago fallido
  EXPIRED    // Tiempo agotado para pago (OXXO/SPEI)
}
