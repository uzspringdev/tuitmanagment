package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private boolean isExists;
    private String errorMessage;
}
