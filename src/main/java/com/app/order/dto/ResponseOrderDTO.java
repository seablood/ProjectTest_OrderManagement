package com.app.order.dto;

import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.domain.ProductOrderRelation;
import com.app.order.util.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseOrderDTO {
    private Long id;

    private List<Product> productList;

    private Integer totalPrice;

    private OrderStatus state;

    public static ResponseOrderDTO toDto(Order order, List<Product> productList){

        return new ResponseOrderDTO(order.getId(), productList, order.getTotalPrice(), order.getState());
    }
}
