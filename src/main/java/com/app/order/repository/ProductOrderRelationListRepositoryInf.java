package com.app.order.repository;

import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.domain.ProductOrderRelation;

public interface ProductOrderRelationListRepositoryInf {
    ProductOrderRelation save(ProductOrderRelation relation);
    ProductOrderRelation findProductOrderRelationByOrderAndProduct(Order order, Product product);
    void delete(ProductOrderRelation relation);
}
