package com.myreflectionthoughts.user.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class UserException extends RuntimeException{
    private String message;

    public UserException(){}

    public UserException(String message){
        this.message = message;
    }
}
