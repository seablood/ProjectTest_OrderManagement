package com.app.order.repository;

import com.app.order.domain.Order;
import com.app.order.util.OrderStatus;

import java.util.List;

public interface OrderListRepositoryInf {
    Order save(Order order);
    Order findById(Long id);
    List<Order> findAllByState(OrderStatus orderStatus);
}
