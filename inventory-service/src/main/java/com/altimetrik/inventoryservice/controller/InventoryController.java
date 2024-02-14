package com.altimetrik.inventoryservice.controller;

import com.altimetrik.inventoryservice.dto.InventoryDto;
import com.altimetrik.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "InventoryController - InventoryController",
        description = "InventoryController expose Inventory Details")
@RestController
@RequestMapping("/api/inventory")
@AllArgsConstructor
public class InventoryController {

    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService inventoryService;

    @Operation(summary = "Add Product Details into Inventory", description = "Product quantity must be greater than 1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @PostMapping
    public ResponseEntity<InventoryDto> addProductIntoInventory(@Valid @RequestBody InventoryDto inventoryDto){
        log.info("InventoryController: addProductIntoInventory");
        InventoryDto addedProductInInventory = this.inventoryService.addProductInInventory(inventoryDto);
        log.info("InventoryDto"+addedProductInInventory);
        return new ResponseEntity<>(addedProductInInventory, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all product based on product_category ", description = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @GetMapping
    public ResponseEntity<List<InventoryDto>> getAllProduct(){
        List<InventoryDto> allProduct = this.inventoryService.getAllProduct();
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }

    @Operation(summary = "Get product by Product name ", description = "Get product by product name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Invalid Product supplied"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @GetMapping("/getByProductName/{productName}")
    public ResponseEntity<InventoryDto> findByProductName(@PathVariable String productName){
        InventoryDto byProductName = this.inventoryService.findByProductName(productName);
        return new ResponseEntity<>(byProductName,HttpStatus.OK);
    }

    @Operation(summary = "Get product by Product Category ", description = "Get product by product category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Invalid Product category supplied"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @GetMapping(value = "/getByProductCategory/{productCategory}")
    public ResponseEntity<List<InventoryDto>> findByProductCategory(@PathVariable String productCategory) {
        List<InventoryDto> productByCategoryList = this.inventoryService.getProductByCategory(productCategory);

        return new ResponseEntity<>(productByCategoryList,HttpStatus.OK);
    }

    @Operation(summary = "Update product details ", description = "Admin can update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Invalid Product category supplied"),
            @ApiResponse(responseCode = "404", description = "Product Id found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @PutMapping("/{productId}")
    public ResponseEntity<InventoryDto> updateInventoryProduct(@PathVariable String productId ,@Valid @RequestBody InventoryDto inventoryDto){

        return inventoryService.findByProductId(productId)
                .map(savedInventory ->{
                    savedInventory.setProductId(inventoryDto.getProductId());
                    savedInventory.setProductName(inventoryDto.getProductName());
                    savedInventory.setProductQuantity(inventoryDto.getProductQuantity());
                    savedInventory.setProductPrice(inventoryDto.getProductPrice());
                    savedInventory.setProductCategory(inventoryDto.getProductCategory());

                    InventoryDto inventoryProduct = inventoryService.updateInventoryProduct(savedInventory);

                    return new ResponseEntity(inventoryProduct,HttpStatus.OK);
                })
                .orElseGet(()-> new ResponseEntity(productId+"Not found",HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Delete product details ", description = "Admin can delete product details from inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Invalid Product category supplied"),
            @ApiResponse(responseCode = "404", description = "Product Id found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteInventoryProduct(@PathVariable String productId){
        this.inventoryService.removeInventoryProduct(productId);
        return new ResponseEntity<>("Product is removed from Inventory!",HttpStatus.OK);
    }





}
