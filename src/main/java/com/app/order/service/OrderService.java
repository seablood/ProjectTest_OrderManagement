package com.app.order.service;

import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.domain.ProductOrderRelation;
import com.app.order.dto.CreateOrderDTO;
import com.app.order.dto.ResponseOrderDTO;
import com.app.order.repository.OrderRepository;
import com.app.order.repository.ProductRepository;
import com.app.order.util.OrderException;
import com.app.order.util.OrderStateComparator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final RelationService relationService;

    @Transactional
    public ResponseOrderDTO save(List<CreateOrderDTO> createOrderDTOList){
        Order order = CreateOrderDTO.toEntity();
        List<Product> productList = new ArrayList<>();

        for(CreateOrderDTO createOrderDTO : createOrderDTOList){
            Product product = productRepository.findById(createOrderDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product를 찾지 못했습니다."));
            if(createOrderDTO.getAmount() > product.getAmount()){
                throw new OrderException(createOrderDTO.getId() + "번 상품의 수량이 부족합니다.");
            }
            productList.add(product);

            product.decreaseAmount(createOrderDTO.getAmount());
            order.increaseTotalPrice(product.getPrice() * createOrderDTO.getAmount());
            relationService.save(order, product);
        }

        return ResponseOrderDTO.toDto(orderRepository.save(order), productList);
    }

    public ResponseOrderDTO findById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order를 찾지 못했습니다."));
        List<Product> productList = productMapping(order.getRelations());

        return ResponseOrderDTO.toDto(order, productList);
    }

    @Transactional
    public ResponseOrderDTO modifyState(Long id, String state){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order를 찾지 못했습니다."));
        List<Product> productList = productMapping(order.getRelations());

        order.modifyState(OrderStateComparator.comparator(state));

        return ResponseOrderDTO.toDto(order, productList);
    }

    public List<Product> productMapping(List<ProductOrderRelation> relationList){
        return relationList.stream()
                .map(productOrderRelation -> productOrderRelation.getProduct())
                .toList();
    }
}
