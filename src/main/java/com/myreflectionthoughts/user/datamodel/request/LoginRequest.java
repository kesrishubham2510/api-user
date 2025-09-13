package com.myreflectionthoughts.user.datamodel.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
