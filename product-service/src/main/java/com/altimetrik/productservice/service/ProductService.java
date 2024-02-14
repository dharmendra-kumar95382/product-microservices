package com.altimetrik.productservice.service;

import com.altimetrik.productservice.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    //ProductDto createProduct(ProductDto productDto);
    ProductDto findByProductName(String productName);
    List<ProductDto> findByProductCategory(String productCategory) ;
}
