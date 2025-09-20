package com.myreflectionthoughts.user.adapter.adapter;

import com.myreflectionthoughts.user.datamodel.request.LoginRequest;
import com.myreflectionthoughts.user.datamodel.request.RegistrationRequest;
import com.myreflectionthoughts.user.datamodel.response.LoginResponse;
import com.myreflectionthoughts.user.datamodel.response.RegistrationResponse;
import feign.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthAdapterInterface {
    RegistrationResponse registerUser(RegistrationRequest registrationRequest);
    Response loginUser(LoginRequest loginRequest);
}
