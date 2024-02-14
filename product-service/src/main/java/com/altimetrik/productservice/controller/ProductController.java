package com.altimetrik.productservice.controller;

import com.altimetrik.productservice.dto.ProductDto;
import com.altimetrik.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProductController - ProductController",
        description = "ProductController expose Product Details")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Operation(summary = "Get product by Product name ", description = "Get product by product name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Invalid Product supplied"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @GetMapping("/getByProductName/{productName}")
    public ResponseEntity<ProductDto> getByProductName(@PathVariable String productName) {
        log.info("ProductController:getByProductName Product Name{}",productName);
        ProductDto byProductName = this.productService.findByProductName(productName);
        return new ResponseEntity<>(byProductName,HttpStatus.OK);
    }

    @Operation(summary = "Get all product based on product_category ", description = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @GetMapping(value = "/getByProductCategory/{productCategory}")
    public ResponseEntity<List<ProductDto>> getByProductCategory(@PathVariable String productCategory) {
        log.info("ProductController:getByProductCategory ProductCategory:{}",productCategory);
        List<ProductDto> byProductCategory = this.productService.findByProductCategory(productCategory);
        return new ResponseEntity<>(byProductCategory,HttpStatus.OK);
    }



}
