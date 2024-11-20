package com.app.order.service;

import com.app.order.domain.Order;
import com.app.order.domain.Product;
import com.app.order.dto.CreateOrderDTO;
import com.app.order.dto.ResponseOrderDTO;
import com.app.order.repository.OrderListRepositoryInf;
import com.app.order.repository.ProductListRepositoryInf;
import com.app.order.util.OrderStateComparator;
import com.app.order.util.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("dev")
public class OrderListService {
    private final OrderListRepositoryInf orderListRepository;
    private final ProductListRepositoryInf productListRepository;
    private final ProductListService productListService;
    private final RelationListService relationListService;

    public ResponseOrderDTO save(List<CreateOrderDTO> createOrderDTOList){
        Order order = CreateOrderDTO.toEntity();
        List<Product> productList = makeProductList(createOrderDTOList);

        decreaseAmount(createOrderDTOList, order);

        return ResponseOrderDTO.toDto(orderListRepository.save(order), productList);
    }

    public ResponseOrderDTO findById(Long id){
        Order order = orderListRepository.findById(id);
        List<Product> productlist = productListService.productMapping(order.getRelations());

        return ResponseOrderDTO.toDto(order, productlist);
    }

    public ResponseOrderDTO modifyState(Long id, String state){
        if(OrderStatus.CANCELED.toString().equals(state)){
            return deleteOrder(id);
        }
        Order order = orderListRepository.findById(id);
        List<Product> productList = productListService.productMapping(order.getRelations());

        order.modifyState(OrderStateComparator.comparator(state));

        return ResponseOrderDTO.toDto(order, productList);
    }

    public List<ResponseOrderDTO> findByState(String state){
        OrderStatus orderStatus = OrderStateComparator.comparator(state);
        List<Order> orderList = orderListRepository.findAllByState(orderStatus);
        List<ResponseOrderDTO> dtoList = new ArrayList<>();

        for(Order order : orderList){
            List<Product> productList = productListService.productMapping(order.getRelations());
            ResponseOrderDTO dto = ResponseOrderDTO.toDto(order, productList);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public ResponseOrderDTO deleteOrder(Long id){
        Order order = orderListRepository.findById(id);

        order.checkState();
        order.modifyState(OrderStatus.CANCELED);
        productListService.cancelAmount(order.getRelations());

        return ResponseOrderDTO.toDto(order, productListService.productMapping(order.getRelations()));
    }

    public List<Product> makeProductList(List<CreateOrderDTO> createOrderDTOList){
        return createOrderDTOList.stream()
                .map(dto -> {
                    Product product = productListRepository.findById(dto.getId());
                    product.checkAmount(dto.getAmount());
                    return product;
                }).toList();
    }

    public void decreaseAmount(List<CreateOrderDTO> createOrderDTOList, Order order){
        createOrderDTOList.stream()
                .forEach(dto -> {
                    Product product = productListRepository.findById(dto.getId());
                    product.decreaseAmount(dto.getAmount());
                    order.increaseTotalPrice(dto.getAmount() * product.getPrice());
                    order.addRelation(relationListService.save(order, product, dto.getAmount()));
                });
    }
}
