package com.altimetrik.inventoryservice.integration;

import com.altimetrik.inventoryservice.dto.InventoryDto;
import com.altimetrik.inventoryservice.entity.Inventory;
import com.altimetrik.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InventoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp(){
        inventoryRepository.deleteAll();
    }

    @Test
    public void addProductIntoInventoryTest() throws Exception {

        InventoryDto inventoryDto = InventoryDto.builder()
                .productId("L002")
                .productName("Laptop")
                .productPrice(40000.00)
                .productQuantity(1)
                .productCategory("Electronics").build();


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

        List<Inventory> listOfInventory = new ArrayList();
        listOfInventory.add( Inventory.builder().productId("L001").productName("Laptop").productPrice(40000.00).productQuantity(1).productCategory("Electronics").build());
        listOfInventory.add( Inventory.builder().productId("L002").productName("Charger").productPrice(1000.00).productQuantity(10).productCategory("Electronics").build());


        inventoryRepository.saveAll(listOfInventory);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(listOfInventory.size())));

    }

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

        inventoryRepository.save(modelMapper.map(inventoryDto,Inventory.class));

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
        List<Inventory> listOfInventory = new ArrayList();
        listOfInventory.add( Inventory.builder().productId("L001").productName("Laptop").productPrice(40000.00).productQuantity(1).productCategory("Electronics").build());
        listOfInventory.add( Inventory.builder().productId("L002").productName("Charger").productPrice(1000.00).productQuantity(10).productCategory("Electronics").build());


        inventoryRepository.saveAll(listOfInventory);
        //When - Action or behaviour that we are going to test
        ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/getByProductCategory/{productCategory}",productCategory));
        //Then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(listOfInventory.size())));
    }

    @Test
    public void updateInventoryProductTest() throws Exception {
        //Given - Precondition or setup
        Inventory savedInventoryDto = Inventory.builder()
                .productId("L001")
                .productName("Laptop")
                .productPrice(40000.00)
                .productQuantity(1)
                .productCategory("Electronics").build();

        inventoryRepository.save(savedInventoryDto);

        InventoryDto updatedInventoryDto =InventoryDto.builder()
                .productId("L001")
                .productName("Charger")
                .productPrice(1000.00)
                .productQuantity(1000)
                .productCategory("Electronics").build();

        //When - Action or behaviour that we are going to test

        //Then - verify the output
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/inventory/{productId}",savedInventoryDto.getProductId())
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


    @Test
    public void deleteInventoryProductTest() throws Exception {
        //Given - Precondition or setup
        Inventory savedInventoryDto = Inventory.builder()
                .productId("L001")
                .productName("Laptop")
                .productPrice(40000.00)
                .productQuantity(1)
                .productCategory("Electronics").build();

        inventoryRepository.save(savedInventoryDto);

        //When - Action or behaviour that we are going to test
        ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.delete("/api/inventory/{productId}",savedInventoryDto.getProductId()));
        //Then - verify the output

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
