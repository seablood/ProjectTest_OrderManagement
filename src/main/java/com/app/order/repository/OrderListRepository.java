package com.app.order.repository;

import com.app.order.domain.Order;
import com.app.order.util.OrderStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class OrderListRepository implements OrderListRepositoryInf{
    private Map<Long, Order> orderMap = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        Long id = Integer.valueOf(orderMap.size()).longValue() + 1L;

        order.setId(id);
        orderMap.put(id, order);
        return order;
    }

    @Override
    public Order findById(Long id) {
        return orderMap.values().stream()
                .filter(order -> order.sameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Order를 찾지 못했습니다."));
    }

    @Override
    public List<Order> findAllByState(OrderStatus orderStatus) {
        return orderMap.values().stream()
                .filter(order -> order.sameState(orderStatus))
                .toList();
    }
}
