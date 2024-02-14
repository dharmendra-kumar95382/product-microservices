package com.altimetrik.inventoryservice.service;

import com.altimetrik.inventoryservice.dto.InventoryDto;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    InventoryDto addProductInInventory(InventoryDto inventoryDto);
    List<InventoryDto> getAllProduct();
    InventoryDto updateInventoryProduct(InventoryDto inventoryDto);
    void removeInventoryProduct(String productId);
    InventoryDto findByProductName(String productName);
    Optional<InventoryDto> findByProductId(String productId);
    List<InventoryDto> getProductByCategory(String productCategory);

}
