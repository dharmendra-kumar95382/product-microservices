package com.altimetrik.inventoryservice.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class InventoryExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleProductNotFound(CategoryNotFoundException exception,
                                                              WebRequest webRequest){

        ErrorDetails details = new ErrorDetails(LocalDateTime.now(),exception.getMessage(),
                "CATEGORY_NOT_FOUND", webRequest.getDescription(false));

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String,String> errorMap = new HashMap<>();
        for(ObjectError allError : ex.getBindingResult().getAllErrors()){
            String fieldName = ((FieldError) allError).getField();
            String fieldValue = allError.getDefaultMessage();
            errorMap.put(fieldName,fieldValue);
        }
        return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
    }

}
