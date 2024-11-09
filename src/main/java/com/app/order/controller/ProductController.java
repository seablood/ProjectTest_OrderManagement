package com.app.order.controller;

import com.app.order.dto.CreateProductDTO;
import com.app.order.dto.ResponseProductDTO;
import com.app.order.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "상품 기능", description = "상품 관련 API")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 추가", description = "상품 정보를 입력 받고 해당 상품을 DB에 저장")
    @PostMapping("/save")
    public ResponseEntity<ResponseProductDTO> save(@RequestBody CreateProductDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(dto));
    }

    @Operation(summary = "상품 검색(ALL)", description = "모든 상품 검색")
    @GetMapping("/find-all")
    public ResponseEntity<List<ResponseProductDTO>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }
}
