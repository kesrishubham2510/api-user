package com.myreflectionthoughts.user.usecase;

import com.myreflectionthoughts.user.datamodel.request.LoginRequest;
import com.myreflectionthoughts.user.datamodel.response.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface LoginUser {

    ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest);
}
