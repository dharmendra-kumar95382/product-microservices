package com.altimetrik.productservice.service.impl;

import com.altimetrik.productservice.dto.ProductDto;
import com.altimetrik.productservice.entity.Product;
import com.altimetrik.productservice.repository.ProductRepository;
import com.altimetrik.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    private final RestTemplate restTemplate;

    @Value("${inventory.productNameUrl}")
    private String productNameUrl;
    @Value("${inventory.productCategoryUrl}")
    private String productCategoryUrl;


    @Override
    public List<ProductDto> findByProductCategory(String productCategory) {
        log.info("ProductServiceImpl:findByProductCategory Product Category:{}",productCategory);
        ProductDto[] forObject = restTemplate.getForObject(productCategoryUrl, ProductDto[].class, productCategory);
        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductDto product : forObject){
            productDtoList.add(product);
        }
        return productDtoList;
    }


    @Override
    public ProductDto findByProductName(String productName)  {
        log.info("ProductServiceImpl:findByProductName Product Name:{}",productName);
        Product product = restTemplate.getForObject(productNameUrl,Product.class,productName);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }




}
