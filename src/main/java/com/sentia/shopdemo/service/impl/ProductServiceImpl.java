package com.sentia.shopdemo.service.impl;

import com.sentia.shopdemo.model.Product;
import com.sentia.shopdemo.repository.ProductRepository;
import com.sentia.shopdemo.service.ProductService;
import com.sentia.shopdemo.util.ProductsFilter;
import graphql.GraphQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final String ENTITY_NAME = "ProductServiceImpl";

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> retrieveAllProducts(ProductsFilter filter, String sort) {
        log.debug(String.format("[%s]:Getting all products", ENTITY_NAME));
        return productRepository.query(filter, sort);
    }

    @Override
    public Product retrieveProductById(String id) {
        log.debug(String.format("[%s]:Getting a product by id", ENTITY_NAME));
        return productRepository.findById(id).orElse(null);
    }
}
