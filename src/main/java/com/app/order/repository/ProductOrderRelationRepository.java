package com.app.order.repository;

import com.app.order.domain.ProductOrderRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRelationRepository extends JpaRepository<ProductOrderRelation, Long> {
}
