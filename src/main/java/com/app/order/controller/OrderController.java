package com.app.order.controller;

import com.app.order.domain.Order;
import com.app.order.dto.CreateOrderDTO;
import com.app.order.dto.ResponseOrderDTO;
import com.app.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<ResponseOrderDTO> save(@RequestBody List<CreateOrderDTO> createOrderDTOList){
        ResponseOrderDTO order = orderService.save(createOrderDTOList);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<ResponseOrderDTO> modifyState(@PathVariable Long id, @RequestBody String state){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.modifyState(id, state));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ResponseOrderDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
    }
}
