package com.app.order.domain;

import com.app.order.util.OrderException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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

    @Column(unique = true)
    @Size(min = 1, max = 100, message = "상품명은 100자 이하까지 허용됩니다.")
    private String name;

    @Max(value = 1_000_000, message = "가격은 100만원 이하까지 허용됩니다.")
    @Min(value = 0, message = "가격은 양수만 허용됩니다.")
    private Integer price;

    @Max(value = 9_999, message = "수량은 9,999개 이하까지 허용됩니다.")
    @Min(value = 0, message = "수량은 양수만 허용됩니다.")
    private Integer amount;

    public void decreaseAmount(Integer deAmount){
        this.amount -= deAmount;
    }

    public void increaseAmount(Integer inAmount){
        this.amount += inAmount;
    }

    public void checkAmount(Integer amount){
        if(this.amount < amount){
            throw new OrderException(this.id + "번 상품의 수량이 부족합니다.");
        }
    }

    @Builder
    public Product(String name, Integer price, Integer amount){
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
}
