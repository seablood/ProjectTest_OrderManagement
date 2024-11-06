package com.app.order.dto;

import com.app.order.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ResponseProductDTO {
    private String name;

    private Integer price;

    private Integer amount;

    public static ResponseProductDTO toDto(Product product){
        return new ResponseProductDTO(product.getName(), product.getPrice(), product.getAmount());
    }
}
