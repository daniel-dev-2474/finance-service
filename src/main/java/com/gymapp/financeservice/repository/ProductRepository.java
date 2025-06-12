package com.gymapp.financeservice.repository;

import com.gymapp.financeservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for accessing product documents in MongoDB.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

  List<Product> findByActiveTrue();

  List<Product> findByType(String type);
}
