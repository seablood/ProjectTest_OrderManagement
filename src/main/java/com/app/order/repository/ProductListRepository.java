package com.app.order.repository;

import com.app.order.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class ProductListRepository implements ProductListRepositoryInf{
    private Map<Long, Product> productHashMap = new ConcurrentHashMap<>();

    @Override
    public Product save(Product product) {
        Long id = Integer.valueOf(productHashMap.size()).longValue() + 1L;
        product.setId(id);
        productHashMap.put(id, product);
        return product;
    }

    @Override
    public Product findById(Long id) {
        return productHashMap.get(id);
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
