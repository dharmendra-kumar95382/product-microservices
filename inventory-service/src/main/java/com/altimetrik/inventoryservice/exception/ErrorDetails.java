package com.altimetrik.inventoryservice.exception;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

    private LocalDateTime localDateTime;
    private String message;
    private String errorValue;
    private String path;


}
