package com.unriviel.api.advice;

import com.unriviel.api.exception.MyFileNotFoundException;
import com.unriviel.api.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MetaDataImageUploadControllerAdvice {


    @ExceptionHandler(value = MyFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleImageNotfound(MyFileNotFoundException e){
        var failureResponse = ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status((int) failureResponse.getStatusCode()).body(failureResponse);
    }
}
