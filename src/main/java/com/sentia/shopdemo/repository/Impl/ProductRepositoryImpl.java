package com.sentia.shopdemo.repository.Impl;

import com.sentia.shopdemo.model.Product;
import com.sentia.shopdemo.repository.ProductRepositoryCustom;
import com.sentia.shopdemo.service.impl.ProductServiceImpl;
import com.sentia.shopdemo.util.ProductsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(ProductRepositoryImpl.class);
    private final String ENTITY_NAME = "ProductRepositoryImpl";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Product> query(ProductsFilter dynamicQuery, String sort) {
        log.debug(String.format("[%s]:Setting query parameters", ENTITY_NAME));

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
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        if (sort != null && !sort.isEmpty()) {
            query.with(Sort.by(Sort.Direction.ASC, sort));
        }

        return mongoTemplate.find(query, Product.class);
    }
}
