package com.app.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_order_relation", indexes = {
        @Index(name = "idx_order_id", columnList = "order_id"),
        @Index(name = "idx_product_id", columnList = "product_id")
})
public class ProductOrderRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "FK_order_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_product_id"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Product product;

    private Integer price;

    private Integer amount;

    public void setId(Long id){
        this.id = id;
    }

    public boolean matchOrderAndProduct(Order order, Product product){
        if(this.order.equals(order) && this.product.equals(product)) return true;
        return false;
    }

    @Builder
    public ProductOrderRelation(Order order, Product product, Integer price, Integer amount){
        this.order = order;
        this.product = product;
        this.price = price;
        this.amount = amount;
    }
}
