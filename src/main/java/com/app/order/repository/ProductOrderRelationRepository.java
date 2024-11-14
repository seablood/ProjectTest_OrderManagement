package com.app.order.repository;

import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.domain.ProductOrderRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOrderRelationRepository extends JpaRepository<ProductOrderRelation, Long> {
    Optional<ProductOrderRelation> findProductOrderRelationByOrderAndProduct(Order order, Product product);
}
