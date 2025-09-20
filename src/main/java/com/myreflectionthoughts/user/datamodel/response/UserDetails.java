package com.myreflectionthoughts.user.datamodel.response;

import lombok.Data;

@Data
public class UserDetails {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String role;
    private String joined;
    private boolean emailVerified;
    private String nextStep;
}
