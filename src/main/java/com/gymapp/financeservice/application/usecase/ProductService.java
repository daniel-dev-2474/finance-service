package com.gymapp.financeservice.application.usecase;

import com.gymapp.financeservice.domain.model.Product;

import java.util.List;

public interface ProductService {

   Product create(Product request);

   Product getById(String id);

   List<Product> getAllActive();

   Product update(String id, Product request);

   void delete(String id);

   List<Product> getByIds(List<String> ids);

}
