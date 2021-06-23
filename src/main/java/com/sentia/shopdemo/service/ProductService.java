package com.sentia.shopdemo.service;

import com.sentia.shopdemo.model.Product;
import com.sentia.shopdemo.util.ProductsFilter;

import java.util.List;

public interface ProductService {

    List<Product> retrieveAllProducts(ProductsFilter filter, String sort);

    Product retrieveProductById(String id);
}
