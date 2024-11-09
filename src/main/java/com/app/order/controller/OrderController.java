package com.app.order.controller;

import com.app.order.dto.CreateOrderDTO;
import com.app.order.dto.ResponseOrderDTO;
import com.app.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = "주문 기능", description = "주문 관련 API")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "상품 ID와 수량을 입력 받아 주문을 생성하고 DB에 저장")
    @PostMapping("/orders")
    public ResponseEntity<ResponseOrderDTO> save(@RequestBody List<CreateOrderDTO> createOrderDTOList){
        ResponseOrderDTO order = orderService.save(createOrderDTOList);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @Operation(summary = "주문 상태 변경", description = "특정 주문의 상태를 변경하여 저장")
    @PutMapping("/orders/{id}")
    public ResponseEntity<ResponseOrderDTO> modifyState(@PathVariable Long id, @RequestBody String state){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.modifyState(id, state));
    }

    @Operation(summary = "주문 검색(ID)", description = "특정 주문을 검색")
    @GetMapping("/orders/{id}")
    public ResponseEntity<ResponseOrderDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
    }

    @Operation(summary = "주문 검색(State)", description = "특정 상태의 주문을 모두 검색")
    @GetMapping("/orders")
    public ResponseEntity<List<ResponseOrderDTO>> findByState(@RequestParam String state){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findByState(state));
    }

    @Operation(summary = "주문 취소", description = "특정 주문의 상태를 확인하고 취소 실행")
    @DeleteMapping("/orders/{id}/cancel")
    public ResponseEntity<ResponseOrderDTO> deleteOrder(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.deleteOrder(id));
    }
}
