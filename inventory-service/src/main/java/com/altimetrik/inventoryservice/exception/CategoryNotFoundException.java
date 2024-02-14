package com.altimetrik.inventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public CategoryNotFoundException(String fieldName,String resourceName){
        // Laptop is currently unavailable in our Inventory.
        super(String.format("%s is currently unavailable in our %s",fieldName,resourceName));
        this.resourceName=resourceName;
        this.fieldName=fieldName;

    }

    public CategoryNotFoundException(String fieldName,String fieldValue,String resourceName){
        // "Product:" Laptop "is not available in sufficient quantity" inventory
        super(String.format("%s: %s is not available in sufficient quantity %s",fieldName,fieldValue,resourceName));
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
        this.resourceName=resourceName;

    }


}
