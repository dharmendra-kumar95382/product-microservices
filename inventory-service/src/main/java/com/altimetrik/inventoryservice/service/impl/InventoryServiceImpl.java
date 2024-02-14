package com.altimetrik.inventoryservice.service.impl;

import com.altimetrik.inventoryservice.dto.InventoryDto;
import com.altimetrik.inventoryservice.entity.Inventory;
import com.altimetrik.inventoryservice.exception.CategoryNotFoundException;
import com.altimetrik.inventoryservice.repository.InventoryRepository;
import com.altimetrik.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final InventoryRepository inventoryRepository;
    private  ModelMapper modelMapper;


    @Override
    public InventoryDto addProductInInventory(InventoryDto inventoryDto) {
        log.info("InventoryServiceImpl:addProductInInventory - ProductName:{} , Product Category:{}",inventoryDto.getProductName(),inventoryDto.getProductCategory());
        Inventory inventory = modelMapper.map(inventoryDto, Inventory.class);
        Inventory savedInventory = inventoryRepository.save(inventory);
        log.info("Product details are saved with {}",savedInventory.getProductId());
        return modelMapper.map(savedInventory,InventoryDto.class);
    }


    @Override
    public List<InventoryDto> getAllProduct() {
        List<Inventory> productList = this.inventoryRepository.findAll();
        if (productList.stream().mapToInt(Inventory::getProductQuantity).count() > 0) {
            return productList.stream().map(inventory -> modelMapper.map(inventory,InventoryDto.class))
                    .sorted(Comparator.comparing(InventoryDto::getProductName)
                            .thenComparing(InventoryDto::getProductCategory).thenComparing(InventoryDto::getProductQuantity))
                    .collect(Collectors.toList());
        }else {
            throw new CategoryNotFoundException("Product", ""+productList.stream().distinct().collect(Collectors.toList()).stream().count(),
                    "is not available in sufficient quantity");
        }
    }

    @Override
    public InventoryDto findByProductName(String productName) {
        log.info("InventoryServiceImpl: findByProductName - ProductName{}",productName);
        Inventory byProductName = this.inventoryRepository.findByProductName(productName);
        return modelMapper.map(byProductName,InventoryDto.class);
    }

    @Override
    public Optional<InventoryDto> findByProductId(String productId) {
        Optional<Inventory> byId = Optional.of(inventoryRepository.findById(productId).get());
        return Optional.ofNullable(modelMapper.map(byId.get(), InventoryDto.class));
    }

    @Override
    public List<InventoryDto> getProductByCategory(String productCategory) {
        log.info("InventoryServiceImpl: getProductByCategory - ProductCategory{}",productCategory);
        List<Inventory> byProductCategory = this.inventoryRepository.findByProductCategory(productCategory);
        List<InventoryDto> collect = byProductCategory.stream().filter(cate -> cate.getProductCategory().equals(productCategory)).map(inventory -> modelMapper.map(inventory, InventoryDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public InventoryDto updateInventoryProduct(InventoryDto inventoryDto) {
        Inventory inventory = modelMapper.map(inventoryDto, Inventory.class);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return modelMapper.map(savedInventory, InventoryDto.class);
    }

    @Override
    public void removeInventoryProduct(String productId) {
        Inventory inventory = inventoryRepository.findById(productId).get();
        if (inventory.getProductId().equals(productId)) {
            this.inventoryRepository.deleteById(productId);
        }else {
            throw new CategoryNotFoundException(productId, "Inventory");
        }
    }



}
