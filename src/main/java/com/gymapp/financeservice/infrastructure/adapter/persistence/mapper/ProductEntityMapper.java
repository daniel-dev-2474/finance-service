package com.gymapp.financeservice.infrastructure.adapter.persistence.mapper;

import com.gymapp.financeservice.domain.model.Product;
import com.gymapp.financeservice.infrastructure.adapter.persistence.entity.ProductEntity;

public class ProductEntityMapper {

  public static Product toDomain(ProductEntity entity) {
    return Product.builder()
        .id(entity.getId())
        .name(entity.getName())
        .description(entity.getDescription())
        .type(entity.getType())
        .price(entity.getPrice())
        .currency(entity.getCurrency())
        .active(entity.isActive())
        .details(entity.getDetails())
        .build();
  }

  public static ProductEntity toEntity(Product domain) {
    return ProductEntity.builder()
        .id(domain.getId())
        .name(domain.getName())
        .description(domain.getDescription())
        .type(domain.getType())
        .price(domain.getPrice())
        .currency(domain.getCurrency())
        .active(domain.isActive())
        .details(domain.getDetails())
        .build();
  }
}
