package com.gymapp.financeservice.infrastructure.adapter;

import com.gymapp.financeservice.domain.model.Purchase;
import com.gymapp.financeservice.domain.repository.PurchaseRepositoryPort;
import com.gymapp.financeservice.infrastructure.adapter.persistence.entity.PurchaseEntity;
import com.gymapp.financeservice.infrastructure.adapter.persistence.mapper.PurchaseEntityMapper;
import com.gymapp.financeservice.infrastructure.adapter.persistence.repository.MongoPurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PurchaseRepositoryAdapter implements PurchaseRepositoryPort {


  private final MongoPurchaseRepository repository;

  @Override
  public Purchase save(Purchase purchase) {
    PurchaseEntity entity = PurchaseEntityMapper.toEntity(purchase);
    PurchaseEntity saved = repository.save(entity);
    return PurchaseEntityMapper.toDomain(saved);
  }

  @Override
  public Optional<Purchase> findById(String id) {
    return repository.findById(id).map(PurchaseEntityMapper::toDomain);

  }


  @Override
  public List<Purchase> findByUserId(String userId) {
    List<PurchaseEntity> purchasesByUser = repository.findByUserId(userId);
    return purchasesByUser.stream().map(PurchaseEntityMapper::toDomain).toList();
  }
}
