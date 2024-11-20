package com.app.order.repository;

import com.app.order.domain.Product;

import java.util.List;

public interface ProductListRepositoryInf {
    Product save(Product product);
    Product findById(Long id);
    List<Product> findAll();
    Product findByName(String name);
}
