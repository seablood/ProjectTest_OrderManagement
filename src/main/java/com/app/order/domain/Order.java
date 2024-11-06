package com.app.order.domain;

import com.app.order.util.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<ProductOrderRelation> relations = new ArrayList<>();

    private Integer totalPrice;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus state;

    @Builder
    public Order(Integer totalPrice, OrderStatus state){
        this.totalPrice = totalPrice;
        this.state = state;
    }
}
