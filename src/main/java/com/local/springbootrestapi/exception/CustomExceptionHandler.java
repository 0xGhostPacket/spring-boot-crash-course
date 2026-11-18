package com.local.springbootrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    // handle Resource Not Found Exception
    @ExceptionHandler( value = {ResourceNotFoundException.class})
    public ResponseEntity<?> HandleResourceNotFount(ResourceNotFoundException ex, WebRequest request)
    {
        // 1- create the payload tht contains the exception details.
        Map<String,Object> body = new HashMap<>();
        body.put("time",new Date());
        body.put("message", ex.getMessage());
        body.put("path" , request.getDescription(false));

        // 2 - return the response
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // handle input validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> HandleInputValidation(MethodArgumentNotValidException ex)
    {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((err)-> {
            String name = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            errors.put(name,message);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}

