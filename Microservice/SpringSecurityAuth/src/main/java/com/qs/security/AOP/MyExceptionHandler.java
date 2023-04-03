package com.qs.security.AOP;

import com.qs.security.domain.response.ExceptionResponse;
import com.qs.security.exception.InvalidCredentialsException;
import com.qs.security.exception.TokenExpiredOrWrongException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleException(Exception e){
        e.printStackTrace();
        return new ResponseEntity(ExceptionResponse.builder()
                .message("Bad Request.")
                .build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {TokenExpiredOrWrongException.class})
    public ResponseEntity handleTokenExpiredOrWrongException(TokenExpiredOrWrongException e){
        e.printStackTrace();
        return new ResponseEntity(ExceptionResponse.builder()
                .message(e.getMessage())
                .build(), HttpStatus.OK);
    }


}