package com.altimetrik.inventoryservice.controller;

import com.altimetrik.inventoryservice.dto.InventoryDto;
import com.altimetrik.inventoryservice.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.*;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@WebMvcTest
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private InventoryService inventoryService;

    @Test
    public void addProductIntoInventoryTest() throws Exception {

       InventoryDto inventoryDto = InventoryDto.builder()
                .productId("L001")
                .productName("Laptop")
                .productPrice(40000.00)
                .productQuantity(1)
                .productCategory("Electronics").build();


        given(inventoryService.addProductInInventory(ArgumentMatchers.any(InventoryDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));


        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inventoryDto)));

        response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId", CoreMatchers.is(inventoryDto.getProductId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName", CoreMatchers.is(inventoryDto.getProductName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice", CoreMatchers.is(inventoryDto.getProductPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productQuantity", CoreMatchers.is(inventoryDto.getProductQuantity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productCategory", CoreMatchers.is(inventoryDto.getProductCategory())));
    }

    @Test
    public void getAllProductTest() throws Exception {

        List<InventoryDto> listOfInventory = new ArrayList();
        listOfInventory.add( InventoryDto.builder().productId("L001").productName("Laptop").productPrice(40000.00).productQuantity(1).productCategory("Electronics").build());
        listOfInventory.add( InventoryDto.builder().productId("L002").productName("Charger").productPrice(1000.00).productQuantity(10).productCategory("Electronics").build());

        given(inventoryService.getAllProduct()).willReturn(listOfInventory);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(listOfInventory.size())));

    }

    //Junit test template
    @Test
    public void findByProductNameTest() throws Exception {
        //Given - Precondition or setup
        String productName = "Laptop";
        InventoryDto inventoryDto = InventoryDto.builder()
                .productId("L001")
                .productName("Laptop")
                .productPrice(40000.00)
                .productQuantity(1)
                .productCategory("Electronics").build();

        given(inventoryService.findByProductName(productName)).willReturn(inventoryDto);
        //When - Action or behaviour that we are going to test
        ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/getByProductName/{productName}",productName));
        //Then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId", CoreMatchers.is(inventoryDto.getProductId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName", CoreMatchers.is(inventoryDto.getProductName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice", CoreMatchers.is(inventoryDto.getProductPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productQuantity", CoreMatchers.is(inventoryDto.getProductQuantity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productCategory", CoreMatchers.is(inventoryDto.getProductCategory())));
    }


    @Test
    public void findByProductCategoryTest() throws Exception {
        //Given - Precondition or setup
        String productCategory = "Electronics";
        List<InventoryDto> listOfInventory = new ArrayList();
        listOfInventory.add( InventoryDto.builder().productId("L001").productName("Laptop").productPrice(40000.00).productQuantity(1).productCategory("Electronics").build());
        listOfInventory.add( InventoryDto.builder().productId("L002").productName("Charger").productPrice(1000.00).productQuantity(10).productCategory("Electronics").build());

        given(inventoryService.getProductByCategory(productCategory)).willReturn(listOfInventory);
        //When - Action or behaviour that we are going to test
        ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/getByProductCategory/{productCategory}",productCategory));
        //Then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(listOfInventory.size())));
    }


    //Junit test template
    @Test
    public void deleteInventoryProductTest() throws Exception {
        //Given - Precondition or setup
        String productId = "L001";
        willDoNothing().given(inventoryService).removeInventoryProduct(productId);
        //When - Action or behaviour that we are going to test
        ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.delete("/api/inventory/{productId}",productId));
        //Then - verify the output

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateInventoryProductTest() throws Exception {
        //Given - Precondition or setup
        String productId = "L001";
        InventoryDto savedInventoryDto = InventoryDto.builder()
                .productId("L001")
                .productName("Laptop")
                .productPrice(40000.00)
                .productQuantity(1)
                .productCategory("Electronics").build();

        InventoryDto updatedInventoryDto =InventoryDto.builder()
                .productId("L001")
                .productName("Charger")
                .productPrice(1000.00)
                .productQuantity(1000)
                .productCategory("Electronics").build();

        //When - Action or behaviour that we are going to test

        given(inventoryService.findByProductId(productId)).willReturn(Optional.of(savedInventoryDto));
        given(inventoryService.updateInventoryProduct(ArgumentMatchers.any(InventoryDto.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //Then - verify the output
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/inventory/{productId}",productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedInventoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.productId", CoreMatchers.is(updatedInventoryDto.getProductId())))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.productName", CoreMatchers.is(updatedInventoryDto.getProductName())))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice", CoreMatchers.is(updatedInventoryDto.getProductPrice())))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.productQuantity", CoreMatchers.is(updatedInventoryDto.getProductQuantity())))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.productCategory", CoreMatchers.is(updatedInventoryDto.getProductCategory())));
    }
}