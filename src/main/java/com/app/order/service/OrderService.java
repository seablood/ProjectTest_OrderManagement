package com.app.order.service;

import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.dto.CreateOrderDTO;
import com.app.order.dto.ResponseOrderDTO;
import com.app.order.repository.OrderRepository;
import com.app.order.repository.ProductRepository;
import com.app.order.util.OrderStateComparator;
import com.app.order.util.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final RelationService relationService;
    private final ProductService productService;

    @Transactional
    public ResponseOrderDTO save(List<CreateOrderDTO> createOrderDTOList){
        Order order = CreateOrderDTO.toEntity();
        List<Product> productList = makeProductList(createOrderDTOList, order);

        return ResponseOrderDTO.toDto(orderRepository.save(order), productList);
    }

    public ResponseOrderDTO findById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order를 찾지 못했습니다."));
        List<Product> productList = productService.productMapping(order.getRelations());

        return ResponseOrderDTO.toDto(order, productList);
    }

    @Transactional
    public ResponseOrderDTO modifyState(Long id, String state){
        if(OrderStatus.CANCELED.toString().equals(state)){
            return deleteOrder(id);
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order를 찾지 못했습니다."));
        List<Product> productList = productService.productMapping(order.getRelations());

        order.modifyState(OrderStateComparator.comparator(state));

        return ResponseOrderDTO.toDto(order, productList);
    }

    public List<ResponseOrderDTO> findByState(String state){
        OrderStatus orderStatus = OrderStateComparator.comparator(state);
        List<Order> orderList = orderRepository.findAllByState(orderStatus);
        List<ResponseOrderDTO> dtoList = orderList.stream()
                .map(order -> {
                    List<Product> productList = productService.productMapping(order.getRelations());
                    ResponseOrderDTO dto = ResponseOrderDTO.toDto(order, productList);
                    return dto;
                }).toList();


        return dtoList;
    }

    @Transactional
    public ResponseOrderDTO deleteOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order를 찾지 못했습니다."));

        order.checkState();
        order.modifyState(OrderStatus.CANCELED);
        productService.cancelAmount(order.getRelations());

        return ResponseOrderDTO.toDto(order, productService.productMapping(order.getRelations()));
    }

    public List<Product> makeProductList(List<CreateOrderDTO> createOrderDTOList, Order order){
        return createOrderDTOList.stream()
                .map(dto -> {
                    Product product = productRepository.findById(dto.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Product를 찾지 못했습니다."));
                    product.checkAmount(dto.getAmount());
                    product.decreaseAmount(dto.getAmount());
                    order.increaseTotalPrice(product.getPrice() * dto.getAmount());
                    relationService.save(order, product, dto.getAmount());
                    return product;
                }).toList();
    }
}
