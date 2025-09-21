package com.myreflectionthoughts.user.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class UserException extends RuntimeException{
    private String key;

    public UserException(){}

    public UserException(String key, String message){
        super(message);
        this.key = key;
    }
}
