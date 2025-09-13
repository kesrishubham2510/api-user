package com.myreflectionthoughts.user.datamodel.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String role;
    private String joined;
    private String token;
    private boolean emailVerified;
}
