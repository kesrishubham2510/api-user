package com.myreflectionthoughts.user.exception;

import lombok.Data;

@Data
public class AuthServiceException extends RuntimeException{
    private String message;
    private String error;
    private String nextStep;

    public AuthServiceException(){}

    public AuthServiceException(String message, String error, String nextStep){
        super(message);
        this.error = error;
        this.nextStep = nextStep;
    }
}
