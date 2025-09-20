package com.myreflectionthoughts.user.datamodel.request;

import lombok.Data;

@Data
public class UpdateSelfRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}
