package com.myreflectionthoughts.user.adapter.feign;

import com.myreflectionthoughts.user.datamodel.request.LoginRequest;
import com.myreflectionthoughts.user.datamodel.request.RegistrationRequest;
import com.myreflectionthoughts.user.datamodel.response.LoginResponse;
import com.myreflectionthoughts.user.datamodel.response.RegistrationResponse;
import org.springframework.web.bind.annotation.*;

public interface AuthClient {

    RegistrationResponse registerUser(@RequestBody RegistrationRequest registrationRequest);
    LoginResponse loginUser(@RequestBody LoginRequest loginRequest);
}
