package com.app.order.domain;

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

    @Builder
    public Product(String name, Integer price, Integer amount){
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
}
