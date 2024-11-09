package com.app.order.repository;

import com.app.order.domain.Order;
import com.app.order.util.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByState(OrderStatus status);
}
