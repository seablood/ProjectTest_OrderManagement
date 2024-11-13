package com.app.order.domain;

import com.app.order.util.OrderCancelException;
import com.app.order.util.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<ProductOrderRelation> relations = new ArrayList<>();

    private int totalPrice;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus state;

    public void increaseTotalPrice(Integer price){
        this.totalPrice += price;
    }

    public void modifyState(OrderStatus state){
        this.state = state;
    }

    public void checkState(){
        this.state.checkState();
        this.state = OrderStatus.CANCELED;
    }

    @Builder
    public Order(){
        this.totalPrice = 0;
        this.state = OrderStatus.CREATED;
    }
}
