package com.sentia.shopdemo.resolver;


import com.sentia.shopdemo.model.Product;
import com.sentia.shopdemo.service.ProductService;
import com.sentia.shopdemo.util.ProductsFilter;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductResolver implements GraphQLQueryResolver {

    private final ProductService productService;

    public ProductResolver(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> allProducts(ProductsFilter filter, String sort) {
        List<Product> productList = productService.retrieveAllProducts(filter, sort);
        return productList;
    }

    public Product product(String id) {
        return productService.retrieveProductById(id);
    }
}
