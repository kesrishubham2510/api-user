package com.myreflectionthoughts.user.exception;

import lombok.Data;

@Data
public class AuthServiceException {
    private String message;
    private String error;
    private String nextStep;
}
