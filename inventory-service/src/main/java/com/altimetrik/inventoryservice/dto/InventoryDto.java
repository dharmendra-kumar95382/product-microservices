package com.altimetrik.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;


@Schema(name = "InventoryDto",description = "Inventory Dto")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryDto   {

    //private static final long serialVersionUID = 4786984495566060779L;

    @Schema(name = "productId",description = "Product id")
    @NotEmpty(message = "ProductId should not be null or empty")
    private String productId;
    @Schema(name = "productName",description = "Product Name")
    @NotEmpty(message = "Product Name should not be null or empty")
    private String productName;
    @Schema(name = "productPrice",description = "Product price should be at least 1")
    @Min(value = 1,message = "Product Price should not be null or empty")
    private Double productPrice;
    @Schema(name = "productQuantity",description = "Product Quantity should be at least 1")
    @Min(value = 1,message = "Product Quantity should not be zero")
    private Integer productQuantity;
    @Schema(name = "productCategory",description = "Product category ")
    @NotEmpty(message = "Product Category should not be null or empty")
    private String productCategory;
}
