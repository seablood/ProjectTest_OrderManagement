package com.app.order.repository;

import com.app.order.domain.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class ProductListRepository implements ProductListRepositoryInf{
    private Map<Long, Product> productHashMap = new ConcurrentHashMap<>();

    @PostConstruct
    void initProducts(){
        Product product1 = new Product(1L, "상품1", 1000, 500);
        Product product2 = new Product(2L, "상품2", 2000, 500);
        Product product3 = new Product(3L, "상품3", 3000, 500);

        productHashMap.put(product1.getId(), product1);
        productHashMap.put(product2.getId(), product2);
        productHashMap.put(product3.getId(), product3);
    }

    @Override
    public Product save(Product product) {
        Long id = Integer.valueOf(productHashMap.size()).longValue() + 1L;
        product.setId(id);
        productHashMap.put(id, product);
        return product;
    }

    @Override
    public Product findById(Long id) {
        return productHashMap.values().stream()
                .filter(product -> product.sameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    @Override
    public List<Product> findAll() {
        return productHashMap.values().stream().toList();
    }

    @Override
    public Product findByName(String name) {
        return productHashMap.values().stream()
                .filter(product -> product.sameName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }
}
