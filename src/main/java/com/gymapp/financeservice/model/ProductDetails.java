package com.gymapp.financeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetails {
  private Integer durationInDays; // Por ejemplo, 30 días para membresía
  private Integer numberOfPasses; // Para paquetes de pases
}
