package com.sentia.shopdemo.resolver;


import com.sentia.shopdemo.model.Product;
import com.sentia.shopdemo.repository.Impl.ProductRepositoryImpl;
import com.sentia.shopdemo.service.ProductService;
import com.sentia.shopdemo.util.ProductsFilter;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductResolver implements GraphQLQueryResolver {

    private static final Logger log = LoggerFactory.getLogger(ProductResolver.class);
    private final String ENTITY_NAME = "ProductResolver";

    private final ProductService productService;

    public ProductResolver(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> allProducts(ProductsFilter filter, String sort) {
        log.debug(String.format("[%s]:Retrieving all products from service", ENTITY_NAME));
        List<Product> productList = productService.retrieveAllProducts(filter, sort);
        return productList;
    }

    public Product product(String id) {
        log.debug(String.format("[%s]:Retrieving product by id from service", ENTITY_NAME));
        return productService.retrieveProductById(id);
    }
}
