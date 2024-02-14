package com.altimetrik.productservice.controller;

import com.altimetrik.productservice.dto.ProductDto;
import com.altimetrik.productservice.service.ProductService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@WebMvcTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getByProductNameTest() throws Exception {
        String productName = "Laptop";
        ProductDto productDto = ProductDto.builder()
                .productId("L001")
                .productName("Laptop")
                .productPrice(40000.00)
                .productQuantity(1)
                .productCategory("Electronics").build();


        given(productService.findByProductName(productName)).willReturn(productDto);
        //When - Action or behaviour that we are going to test
        ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.get("/api/products/getByProductName/{productName}",productName));
        //Then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId", CoreMatchers.is(productDto.getProductId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName", CoreMatchers.is(productDto.getProductName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice", CoreMatchers.is(productDto.getProductPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productQuantity", CoreMatchers.is(productDto.getProductQuantity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productCategory", CoreMatchers.is(productDto.getProductCategory())));
    }

    @Test
    public void getByProductCategoryTest() throws Exception {

        String productCategory = "Electronics";
        List<ProductDto> listOfProductDto = new ArrayList();
        listOfProductDto.add( ProductDto.builder().productId("L001").productName("Laptop").productPrice(40000.00).productQuantity(1).productCategory("Electronics").build());
        listOfProductDto.add( ProductDto.builder().productId("L002").productName("Charger").productPrice(1000.00).productQuantity(10).productCategory("Electronics").build());

        given(productService.findByProductCategory(productCategory)).willReturn(listOfProductDto);
        //When - Action or behaviour that we are going to test
        ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.get("/api/products/getByProductCategory/{productCategory}",productCategory));
        //Then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(listOfProductDto.size())));

    }

}