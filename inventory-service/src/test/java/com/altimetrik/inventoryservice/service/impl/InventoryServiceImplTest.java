package com.altimetrik.inventoryservice.service.impl;

import com.altimetrik.inventoryservice.dto.InventoryDto;
import com.altimetrik.inventoryservice.entity.Inventory;
import com.altimetrik.inventoryservice.repository.InventoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;


import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;
    @InjectMocks
    private InventoryServiceImpl inventoryService;
    @Mock
    private ModelMapper modelMapper;
    private InventoryDto inventoryDto;
    private Inventory inventory;

    @BeforeEach
    public void setUp(){
        inventoryDto = InventoryDto.builder()
                .productId("L001")
                .productName("Laptop")
                .productPrice(40000.00)
                .productQuantity(1)
                .productCategory("Electronics").build();
        inventory = Inventory.builder()
                .productId("L001")
                .productName("Laptop")
                .productPrice(40000.00)
                .productQuantity(1)
                .productCategory("Electronics").build();
    }

    @Test
   public void addProductInInventoryTest() {
       // MockitoAnnotations.openMocks(this);
        given(modelMapper.map(inventoryDto,Inventory.class)).willReturn(inventory);
        given(inventoryRepository.save(inventory)).willReturn(inventory);
        given(modelMapper.map(inventory,InventoryDto.class)).willReturn(inventoryDto);

        InventoryDto result = inventoryService.addProductInInventory(inventoryDto);
        System.out.println("result:"+result);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getProductPrice()).isEqualTo(40000.00);
        verify(inventoryRepository).save(inventory);

    }

    @Test
    public void getAllProductTest(){

        Inventory inventory1 = Inventory.builder()
                .productId("L003")
                .productName("Mobile")
                .productPrice(30000.00)
                .productQuantity(10)
                .productCategory("Electronics")
                .build();
        InventoryDto inventoryDto1 = InventoryDto.builder()
                .productId("L003")
                .productName("Mobile")
                .productPrice(30000.00)
                .productQuantity(10)
                .productCategory("Electronics")
                .build();

        List<Inventory> listOfInventory = Arrays.asList(inventory,inventory1);
        given(inventoryRepository.findAll()).willReturn(listOfInventory);

        given(modelMapper.map(inventory,InventoryDto.class)).willReturn(inventoryDto);
        given(modelMapper.map(inventory1,InventoryDto.class)).willReturn(inventoryDto1);

        List<InventoryDto> allProduct = inventoryService.getAllProduct();
        System.out.println(allProduct.get(0));
        System.out.println(allProduct.get(1));

        //Then - verify the output
        Assertions.assertThat(allProduct.size()).isEqualTo(2);
    }

    @Test
    public void findByProductNameTest(){
        String productName = "Laptop";
        given(inventoryRepository.findByProductName(productName)).willReturn(inventory);
        given(modelMapper.map(inventory,InventoryDto.class)).willReturn(inventoryDto);

        InventoryDto byProductName = inventoryService.findByProductName(productName);

        Assertions.assertThat(byProductName).isNotNull();
    }

    @Test
    public void getProductByCategoryTest(){


        String productCategory = "Electronics";

        List<Inventory> list = new ArrayList<>();
        list.add(inventory);
        List<InventoryDto> listDto = new ArrayList<>();
        listDto.add(inventoryDto);

        given(modelMapper.map(inventory,InventoryDto.class)).willReturn(inventoryDto);

        given(inventoryRepository.findByProductCategory(productCategory)
                        .stream()
                        .filter(cate -> cate.getProductCategory().equals(productCategory))
                        .map(category -> modelMapper.map(category, Inventory.class))
                        .collect(Collectors.toList())).willReturn(list);


        List<InventoryDto> productByCategory = inventoryService.getProductByCategory(productCategory);
        System.out.println(productByCategory.get(0));

        Assertions.assertThat(productByCategory.size()).isEqualTo(1);

    }

    @Test
    public void removeInventoryProductTest(){

        String productId = "L001";
        given(inventoryRepository.findById(productId)).willReturn(Optional.ofNullable(inventory));

         willDoNothing().given(inventoryRepository).deleteById(productId);
        inventoryService.removeInventoryProduct(productId);

        verify(inventoryRepository,times(1)).deleteById(inventoryDto.getProductId());

    }

    @Test
    public void updateInventoryProductTest(){
        //Given - Precondition or setup
        given(modelMapper.map(inventoryDto,Inventory.class)).willReturn(inventory);
        given(inventoryRepository.save(inventory)).willReturn(inventory);

        given(modelMapper.map(inventory,InventoryDto.class)).willReturn(inventoryDto);

        //When - Action or behaviour that we are going to test
        InventoryDto inventoryProduct = inventoryService.updateInventoryProduct(inventoryDto);

        //Then - verify the output
        Assertions.assertThat(inventoryProduct.getProductName()).isNotNull();
    }

    //Junit test template
    @Test
    public void findByProductIdTest(){
        //Given - Precondition or setup
        String productId = "L001";
        given(inventoryRepository.findById(productId)).willReturn(Optional.ofNullable(inventory));
        given(modelMapper.map(inventory,InventoryDto.class)).willReturn(inventoryDto);

        //When - Action or behaviour that we are going to test
        Optional<InventoryDto> byProductId = inventoryService.findByProductId(productId);

        //Then - verify the output
        Assertions.assertThat(byProductId.get()).isNotNull();

    }


}