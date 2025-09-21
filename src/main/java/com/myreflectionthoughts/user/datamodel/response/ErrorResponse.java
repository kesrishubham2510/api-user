package com.myreflectionthoughts.user.datamodel.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private String key;
    private String message;
}
