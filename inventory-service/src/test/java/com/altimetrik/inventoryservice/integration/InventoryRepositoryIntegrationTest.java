package com.altimetrik.inventoryservice.integration;

import com.altimetrik.inventoryservice.entity.Inventory;
import com.altimetrik.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InventoryRepositoryIntegrationTest {

    @Autowired
    private InventoryRepository inventoryRepository;
    private Inventory inventory;

    @BeforeEach
    public void setUp(){
        inventory = Inventory.builder()
                .productId("L0001")
                .productName("Laptop")
                .productPrice(50000.00D)
                .productQuantity(10)
                .productCategory("Electronics")
                .build();

    }
    @Test
    public void findByProductNameTest() {

        inventoryRepository.save(inventory);
        Inventory byProductName = inventoryRepository.findByProductName(inventory.getProductName());
        assertThat(byProductName).isNotNull();

    }



    @Test
    public void findByProductCategoryTest() {
        inventoryRepository.save(inventory);

        List<Inventory> byProductCategory = inventoryRepository.findByProductCategory(inventory.getProductCategory());
        assertThat(byProductCategory).isNotNull();
        assertThat(byProductCategory.size()).isEqualTo(2);
    }
}