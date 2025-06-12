package com.gymapp.financeservice.mapper;

import com.gymapp.financeservice.dto.ProductDetailsDTO;
import com.gymapp.financeservice.dto.ProductRequest;
import com.gymapp.financeservice.dto.ProductResponse;
import com.gymapp.financeservice.model.Product;
import com.gymapp.financeservice.model.ProductDetails;

import java.math.BigDecimal;

public final class ProductMapper {

   public static Product toEntity(ProductRequest request) {
     return Product.builder()
         .name(request.getName())
         .description(request.getDescription())
         .price(request.getPrice())
         .active(request.isActive())
         .type(request.getType())
         .currency(request.getCurrency())
         .details(toEntityDetails(request.getDetails()))
         .build();
   }
   public static ProductResponse toResponse(Product product) {
     return ProductResponse.builder()
         .id(product.getId())
         .name(product.getName())
         .description(product.getDescription())
         .price(product.getPrice())
         .active(product.isActive())
         .type(product.getType())
         .currency(product.getCurrency())
         .details(toResponseDetails(product.getDetails()))
         .build();
   }

  private static ProductDetails toEntityDetails(ProductDetailsDTO dto) {
    if (dto == null) return null;
    return ProductDetails.builder()
        .durationInDays(dto.getDurationInDays())
        .numberOfPasses(dto.getNumberOfPasses())
        .build();
  }

  private static ProductDetailsDTO toResponseDetails(ProductDetails details) {
    if (details == null) return null;
    return ProductDetailsDTO.builder()
        .durationInDays(details.getDurationInDays())
        .numberOfPasses(details.getNumberOfPasses())
        .build();
  }
}
