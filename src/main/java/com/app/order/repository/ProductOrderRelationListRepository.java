package com.app.order.repository;

import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.domain.ProductOrderRelation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class ProductOrderRelationListRepository implements ProductOrderRelationListRepositoryInf{
    private Map<Long, ProductOrderRelation> relationMap = new ConcurrentHashMap<>();

    @Override
    public ProductOrderRelation save(ProductOrderRelation relation) {
        Long id = Integer.valueOf(relationMap.size()).longValue() + 1L;

        relation.setId(id);
        relationMap.put(id, relation);
        return relation;
    }

    @Override
    public ProductOrderRelation findProductOrderRelationByOrderAndProduct(Order order, Product product) {
        return relationMap.values().stream()
                .filter(relation -> relation.matchOrderAndProduct(order, product))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("주문 상품을 찾지 못했습니다."));
    }

    @Override
    public void delete(ProductOrderRelation relation) {
        relationMap.remove(relation.getId());
    }
}
