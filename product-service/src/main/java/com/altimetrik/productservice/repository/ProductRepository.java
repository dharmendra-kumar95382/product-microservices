package com.altimetrik.productservice.repository;

import com.altimetrik.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findByProductName(String productName);
    List<Product> findByProductCategory(String productCategory);
}
