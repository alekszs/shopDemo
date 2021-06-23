package com.sentia.shopdemo.repository;

import com.sentia.shopdemo.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

    @Query("{ 'label': ?0, 'type': ?1}")
    List<Product> findAllByLabelAndType(String label, String type);
}
