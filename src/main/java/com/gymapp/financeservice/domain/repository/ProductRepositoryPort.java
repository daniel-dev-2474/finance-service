package com.gymapp.financeservice.domain.repository;

import com.gymapp.financeservice.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {

  Product save(Product product);

  Optional<Product> findById(String id);

  List<Product> getAllActive();

  List<Product> findAllByIds(List<String> ids);

  void delete(String id);

}
