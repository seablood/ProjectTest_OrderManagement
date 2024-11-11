package com.app.order.service;

import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.domain.ProductOrderRelation;
import com.app.order.repository.ProductOrderRelationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationService {
    private final ProductOrderRelationRepository productOrderRelationRepository;

    @Transactional
    public void save(Order order, Product product, Integer amount){
        ProductOrderRelation relation = ProductOrderRelation.builder()
                .order(order)
                .product(product)
                .amount(amount)
                .build();

        productOrderRelationRepository.save(relation);
    }
}
