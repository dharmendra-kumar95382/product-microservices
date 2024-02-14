package com.altimetrik.productservice.dto;


import com.altimetrik.productservice.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;

@Schema(name = "ProductDto",description = "Product Dto")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto  {

    @Schema(name = "productId",description = "Product id")
    @NotEmpty(message = "ProductId should not be null or empty")
    private String productId;
    @Schema(name = "productName",description = "Product Name")
    @NotEmpty(message = "Product name should not be null or empty")
    private String productName;
    @Schema(name = "productPrice",description = "Product Price")
    @NotEmpty(message = "Product price should not be null or empty")
    private Double productPrice;
    @Schema(name = "productQuantity",description = "Product Quantity")
    @Min(value = 1,message = "Product Quantity is not sufficient in our Inventory")
    private Integer productQuantity;
    @Schema(name = "productCategory",description = "Product Category")
    @NotEmpty(message = "Product category should not be null or empty")
    private String productCategory;


    public ProductDto(String productName) {
        this.productName = productName;
    }
}
