package com.gymapp.financeservice.repository;

import com.gymapp.financeservice.model.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for accessing purchase documents in MongoDB.
 */
@Repository
public interface PurchaseRepository extends MongoRepository<Purchase, String> {

  List<Purchase> findByUserId(String userId);
}
