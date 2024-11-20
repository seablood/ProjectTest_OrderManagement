package com.app.order.domain;

import com.app.order.util.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void decreaseTotalPrice(Integer price){
        this.totalPrice -= price;
    }

    public void modifyState(OrderStatus state){
        this.state = state;
    }

    public void checkState(){
        this.state.checkState();
    }

    public void setId(Long id){
        this.id = id;
    }

    public boolean sameState(OrderStatus orderStatus){
        if(this.state == orderStatus) return true;
        return false;
    }

    public boolean sameId(Long id){
        return this.id.equals(id);
    }

    public void addRelation(ProductOrderRelation relation){
        this.relations.add(relation);
    }

    public void deleteRelation(ProductOrderRelation relation){
        this.relations.remove(relation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (getClass() != o.getClass())) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Builder
    public Order(){
        this.totalPrice = 0;
        this.state = OrderStatus.CREATED;
    }
}
