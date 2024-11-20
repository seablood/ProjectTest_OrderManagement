package com.app.order.service;

import com.app.order.domain.Product;
import com.app.order.domain.ProductOrderRelation;
import com.app.order.dto.CreateProductDTO;
import com.app.order.dto.ResponseProductDTO;
import com.app.order.repository.ProductListRepositoryInf;
import com.app.order.util.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("dev")
public class ProductListService {
    private final ProductListRepositoryInf productListRepository;
    private final ValidationService validationService;

    public ResponseProductDTO save(CreateProductDTO dto){
        Product product = CreateProductDTO.toEntity(dto);
        validationService.checkValid(product);

        return ResponseProductDTO.toDto(productListRepository.save(product));
    }

    public List<ResponseProductDTO> findAll(){
        return productListRepository.findAll().stream()
                .map(product -> ResponseProductDTO.toDto(product))
                .toList();
    }

    public List<Product> productMapping(List<ProductOrderRelation> relationList){
        return relationList.stream()
                .map(productOrderRelation -> productOrderRelation.getProduct())
                .toList();
    }

    public void cancelAmount(List<ProductOrderRelation> relationList){
        relationList.stream()
                .forEach(productOrderRelation -> productOrderRelation.getProduct().increaseAmount(productOrderRelation.getAmount()));
    }
}
