package com.app.order.dto;

import com.app.order.domain.Order;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {
    @NotNull(message = "ID를 입력하세요.")
    private Long id;

    @NotNull(message = "수량을 입력하세요.")
    private Integer amount;

    public static Order toEntity(){
        return Order.builder().build();
    }
}
