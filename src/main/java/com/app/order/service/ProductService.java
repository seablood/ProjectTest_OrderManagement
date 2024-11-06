package com.app.order.service;

import com.app.order.domain.Product;
import com.app.order.dto.CreateProductDTO;
import com.app.order.dto.ResponseProductDTO;
import com.app.order.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public ResponseProductDTO save(CreateProductDTO dto){
        return ResponseProductDTO.toDto(productRepository.save(CreateProductDTO.toEntity(dto)));
    }

    public Product findById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

    public List<ResponseProductDTO> findAll(){
        return productRepository.findAll().stream()
                .map(product -> ResponseProductDTO.toDto(product))
                .toList();
    }
}
