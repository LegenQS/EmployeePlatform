package com.qs.security.exception;

public class TokenExpiredOrWrongException extends RuntimeException{

    public TokenExpiredOrWrongException(String message){
        super(String.format(message));

    }
}
