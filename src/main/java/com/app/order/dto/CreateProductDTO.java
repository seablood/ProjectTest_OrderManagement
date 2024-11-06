package com.app.order.dto;

import com.app.order.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    private String name;

    private Integer price;

    private Integer amount;

    public static Product toEntity(CreateProductDTO dto){
        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .amount(dto.getAmount())
                .build();
    }
}
