package com.gymapp.financeservice.infrastructure.adapter.persistence.repository;

import com.gymapp.financeservice.infrastructure.adapter.persistence.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for accessing product documents in MongoDB.
 */
@Repository
public interface MongoProductRepository extends MongoRepository<ProductEntity, String> {

  List<ProductEntity> findByActiveTrue();

  List<ProductEntity> findByType(String type);
}
