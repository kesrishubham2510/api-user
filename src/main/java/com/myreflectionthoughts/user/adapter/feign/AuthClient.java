package com.myreflectionthoughts.user.adapter.feign;

import com.myreflectionthoughts.user.config.FeignConfig;
import com.myreflectionthoughts.user.datamodel.request.LoginRequest;
import com.myreflectionthoughts.user.datamodel.request.RegistrationRequest;
import com.myreflectionthoughts.user.datamodel.response.LoginResponse;
import com.myreflectionthoughts.user.datamodel.response.RegistrationResponse;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "http://localhost:8000", configuration = FeignConfig.class)
public interface AuthClient {

    @PostMapping(value = "/api-auth/register", consumes = "application/json", produces = "application/json")
    RegistrationResponse registerUser(@RequestBody RegistrationRequest registrationRequest);

    @PostMapping(value = "/api-auth/generate-token", consumes = "application/json", produces = "text/json")
    Response loginUser(@RequestBody LoginRequest loginRequest);
}
