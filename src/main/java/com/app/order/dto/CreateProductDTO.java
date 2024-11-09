package com.app.order.dto;

import com.app.order.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    @NotBlank(message = "상품명을 다시 입력해주세요.")
    private String name;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer price;

    @NotNull(message = "수량을 입력해주세요.")
    private Integer amount;

    public static Product toEntity(CreateProductDTO dto){
        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .amount(dto.getAmount())
                .build();
    }
}
