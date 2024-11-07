package com.app.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    private Integer amount;

    public void decreaseAmount(Integer deAmount){
        this.amount -= deAmount;
    }

    @Builder
    public Product(String name, Integer price, Integer amount){
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
}
