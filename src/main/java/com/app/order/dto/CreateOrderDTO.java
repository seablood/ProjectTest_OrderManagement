package com.app.order.dto;

import com.app.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {
    private Long id;

    private Integer amount;

    public static Order toEntity(){
        return Order.builder().build();
    }
}
