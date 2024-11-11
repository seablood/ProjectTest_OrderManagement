package com.app.order.service;

import com.app.order.domain.Product;
import com.app.order.domain.ProductOrderRelation;
import com.app.order.dto.CreateProductDTO;
import com.app.order.dto.ResponseProductDTO;
import com.app.order.repository.ProductRepository;
import com.app.order.util.ValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ValidationService validationService;

    @Transactional
    public ResponseProductDTO save(CreateProductDTO dto){
        Product product = CreateProductDTO.toEntity(dto);
        validationService.checkValid(product);

        return ResponseProductDTO.toDto(productRepository.save(product));
    }

    public List<ResponseProductDTO> findAll(){
        return productRepository.findAll().stream()
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
