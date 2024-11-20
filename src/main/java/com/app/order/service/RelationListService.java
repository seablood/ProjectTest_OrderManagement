package com.app.order.service;

import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.domain.ProductOrderRelation;
import com.app.order.repository.OrderListRepositoryInf;
import com.app.order.repository.ProductListRepositoryInf;
import com.app.order.repository.ProductOrderRelationListRepositoryInf;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("dev")
public class RelationListService {
    private final ProductOrderRelationListRepositoryInf relationListRepository;
    private final ProductListRepositoryInf productListRepository;
    private final OrderListRepositoryInf orderListRepository;

    public ProductOrderRelation save(Order order, Product product, Integer amount){
        ProductOrderRelation relation = ProductOrderRelation.builder()
                .order(order)
                .product(product)
                .price(product.getPrice() * amount)
                .amount(amount)
                .build();

        return relationListRepository.save(relation);
    }

    public void deleteRelation(Long id, String name){
        Order order = orderListRepository.findById(id);
        order.checkState();

        Product product = productListRepository.findByName(name);
        ProductOrderRelation relation = relationListRepository.findProductOrderRelationByOrderAndProduct(order, product);

        order.decreaseTotalPrice(relation.getPrice());
        product.increaseAmount(relation.getAmount());
        order.deleteRelation(relation);

        relationListRepository.delete(relation);
    }
}
