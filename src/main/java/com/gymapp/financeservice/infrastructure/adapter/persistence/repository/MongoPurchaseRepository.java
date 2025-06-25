package com.gymapp.financeservice.infrastructure.adapter.persistence.repository;

import com.gymapp.financeservice.infrastructure.adapter.persistence.entity.PurchaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing purchase documents in MongoDB.
 */
@Repository
public interface MongoPurchaseRepository extends MongoRepository<PurchaseEntity, String> {

  List<PurchaseEntity> findByUserId(String userId);

}
