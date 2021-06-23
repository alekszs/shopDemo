package com.sentia.shopdemo.repository;

import com.sentia.shopdemo.model.Product;
import com.sentia.shopdemo.util.ProductsFilter;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> query(ProductsFilter dynamicQuery, String sort);
}
