package com.tonio.spring6restmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController{
    @ExceptionHandler(TransactionSystemException.class)
    ResponseEntity<List<Map<String, String>>> handleJPAViolation(TransactionSystemException e){
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();

        if(e.getCause().getCause() instanceof ConstraintViolationException ve){

            List<Map<String,String>> errors = ve.getConstraintViolations().stream()
                                                .map(constraintViolation -> {
                                                    Map<String, String> errorMap = new HashMap<>();
                                                    errorMap.put(constraintViolation.getPropertyPath().toString(),
                                                            constraintViolation.getMessage());
                                                    return errorMap;
                                                }).toList();

            return responseEntity.body(errors);
        }

        return responseEntity.build();

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<List<Map<String,String>>> handleBindErrors(MethodArgumentNotValidException exception){

        var errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String > errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errorList);
    }
}
