package com.example.employee.advice;

import com.example.employee.exception.ErrorDetails;
import com.example.employee.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetails handleUserNotFoundException(UserNotFoundException une, WebRequest webRequest) {
        return ErrorDetails.build(une.getMessage(), webRequest.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetails handleInternalServerErrorException(Exception e, WebRequest webRequest) {

        //ResourceNotFoundException r = null;
        return ErrorDetails.build(e.getMessage(), webRequest.getDescription(false));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorDetails> handleInvalidArgument(MethodArgumentNotValidException ex) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorDetails.add(ErrorDetails.build(error.getField(), error.getDefaultMessage()));
            //errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorDetails;
    }
}
