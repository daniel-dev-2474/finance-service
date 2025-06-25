package com.gymapp.financeservice.infrastructure.adapter;

import com.gymapp.financeservice.domain.model.Product;
import com.gymapp.financeservice.domain.repository.ProductRepositoryPort;
import com.gymapp.financeservice.infrastructure.adapter.persistence.entity.ProductEntity;
import com.gymapp.financeservice.infrastructure.adapter.persistence.mapper.ProductEntityMapper;
import com.gymapp.financeservice.infrastructure.adapter.persistence.repository.MongoProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {


  private final MongoProductRepository repository;
  @Override
  public Product save(Product product) {
    ProductEntity entity = ProductEntityMapper.toEntity(product);
    ProductEntity save = repository.save(entity);
    return ProductEntityMapper.toDomain(save);
  }

  @Override
  public Optional<Product> findById(String id) {
    return repository.findById(id).map(ProductEntityMapper::toDomain);
  }

  @Override
  public List<Product> getAllActive() {
    return repository.findByActiveTrue().stream()
        .map(ProductEntityMapper::toDomain).toList();
  }

  @Override
  public List<Product> findAllByIds(List<String> ids) {
    return repository.findAllById(ids).stream()
        .map(ProductEntityMapper::toDomain).toList();
  }



  @Override
  public void delete(String id) {
    repository.deleteById(id);
  }
}
