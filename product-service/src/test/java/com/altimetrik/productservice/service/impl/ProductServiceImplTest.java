package com.altimetrik.productservice.service.impl;


import com.altimetrik.productservice.dto.ProductDto;
import com.altimetrik.productservice.entity.Product;
import com.altimetrik.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void findByProductNameTest(){
        //Given - Precondition or setup
        String productName = "Laptop";

        Product product = Product.builder().productName(productName).build();

        ProductDto productDto = ProductDto.builder()
                        .productId("L002").productName(productName).productPrice(50000.00).productQuantity(1000).productCategory("Electronics").build();

        ReflectionTestUtils.setField(productService,"productNameUrl","http://INVENTORY-SERVICE/api/inventory/getByProductName/{productName}");

        when(restTemplate.getForObject(anyString(),eq(Product.class),anyString())).thenReturn(product);
        when(modelMapper.map(any(),eq(ProductDto.class))).thenReturn(productDto);

        ProductDto result = productService.findByProductName(productName);
        System.out.println(result);

        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyString());
        verify(modelMapper,times(1)).map(any(),eq(ProductDto.class));
        Assertions.assertEquals(productName,result.getProductName());


    }

    @Test
    public void findByProductCategoryTest(){
        String productCategory = "Electronics";

        ProductDto[] productDto = new ProductDto[2];
        productDto[0] = ProductDto.builder()
                .productId("L002").productName("Laptop").productPrice(50000.00).productQuantity(1000).productCategory("Electronics").build();
        productDto[1] = ProductDto.builder()
                .productId("M002").productName("Mobile").productPrice(50000.00).productQuantity(100).productCategory("Electronics").build();

        ReflectionTestUtils.setField(productService,"productCategoryUrl","http://INVENTORY-SERVICE/api/inventory/getByProductCategory/{productCategory}");

        when(restTemplate.getForObject(anyString(),eq(ProductDto[].class),anyString())).thenReturn(productDto);

        List<ProductDto> result = productService.findByProductCategory(productCategory);
        System.out.println(result);

        Assertions.assertEquals(2,result.size());
        Assertions.assertEquals(productCategory,result.get(0).getProductCategory());
        Assertions.assertEquals(productCategory,result.get(1).getProductCategory());


    }

}