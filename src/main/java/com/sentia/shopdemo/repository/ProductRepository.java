package com.sentia.shopdemo.repository;

import com.sentia.shopdemo.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

    @Query("{ 'label': ?0, 'type': ?1}")
    List<Product> findAllByLabelAndType(String label, String type);


    @Query("{ '_id': ?0}")
    Optional<Product> findById(String id);
}
