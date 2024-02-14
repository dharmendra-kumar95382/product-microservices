package com.altimetrik.inventoryservice.repository;

import com.altimetrik.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,String> {
    Inventory findByProductName(String productName);
    List<Inventory> findByProductCategory(String productCategory);


}
