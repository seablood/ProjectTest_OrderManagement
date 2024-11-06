package com.app.order.controller;

import com.app.order.dto.CreateProductDTO;
import com.app.order.dto.ResponseProductDTO;
import com.app.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ResponseProductDTO> save(@RequestBody CreateProductDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(dto));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<ResponseProductDTO>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }
}
