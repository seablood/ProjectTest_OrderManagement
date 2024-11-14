package com.app.order.service;

import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.domain.ProductOrderRelation;
import com.app.order.repository.OrderRepository;
import com.app.order.repository.ProductOrderRelationRepository;
import com.app.order.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationService {
    private final ProductOrderRelationRepository productOrderRelationRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    @Transactional
    public void save(Order order, Product product, Integer amount){
        ProductOrderRelation relation = ProductOrderRelation.builder()
                .order(order)
                .product(product)
                .price(product.getPrice() * amount)
                .amount(amount)
                .build();

        productOrderRelationRepository.save(relation);
    }

    @Transactional
    public void deleteRelation(Long id, String name){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order를 찾지 못했습니다."));
        order.checkState();

        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Product룰 찾지 못했습니다."));
        ProductOrderRelation relation = productOrderRelationRepository.findProductOrderRelationByOrderAndProduct(order, product)
                .orElseThrow(() -> new IllegalArgumentException("주문 상품을 찾지 못했습니다."));

        order.decreaseTotalPrice(relation.getPrice());
        product.increaseAmount(relation.getAmount());

        productOrderRelationRepository.delete(relation);
    }
}
