package com.sentia.shopdemo.repository.Impl;

import com.sentia.shopdemo.model.Product;
import com.sentia.shopdemo.repository.ProductRepositoryCustom;
import com.sentia.shopdemo.util.ProductsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Product> query(ProductsFilter dynamicQuery, String sort) {
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();

        if (dynamicQuery.getLabel() != null) {
            criteria.add(Criteria.where("label").regex(dynamicQuery.getLabel()));
        }
        if (dynamicQuery.getType() != null) {
            criteria.add(Criteria.where("type").regex(dynamicQuery.getType()));
        }
        if (dynamicQuery.getColor() != null) {
            criteria.add(Criteria.where("color").regex(dynamicQuery.getColor()));
        }
        if (dynamicQuery.getMinPrice() != null) {
            criteria.add(Criteria.where("price").gte(dynamicQuery.getMinPrice()));
        }
        if (dynamicQuery.getMaxPrice() != null) {
            criteria.add(Criteria.where("price").lte(dynamicQuery.getMaxPrice()));
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        if (!sort.isEmpty()) {
            query.with(Sort.by(Sort.Direction.ASC, sort));
        }

        return mongoTemplate.find(query, Product.class);
    }
}
