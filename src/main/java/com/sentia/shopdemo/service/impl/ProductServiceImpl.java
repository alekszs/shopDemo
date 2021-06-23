package com.sentia.shopdemo.service.impl;

import com.sentia.shopdemo.model.Product;
import com.sentia.shopdemo.repository.ProductRepository;
import com.sentia.shopdemo.service.ProductService;
import com.sentia.shopdemo.util.ProductsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final String ENTITY_NAME = "ProductService";

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> retrieveAllProducts(ProductsFilter filter, String sort) {
        return productRepository.query(filter, sort);
    }

    @Override
    public Product retrieveProductById(String id) {
        return productRepository.findById(id).orElse(new Product());
    }
}
