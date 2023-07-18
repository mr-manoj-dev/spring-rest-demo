package com.example.employee.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "build")
public class ErrorDetails {
    private String message;
    private String details;
}
