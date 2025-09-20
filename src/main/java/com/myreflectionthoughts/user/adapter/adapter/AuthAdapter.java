package com.myreflectionthoughts.user.adapter.adapter;

import com.myreflectionthoughts.user.adapter.feign.AuthClient;
import com.myreflectionthoughts.user.adapter.feign.FeignErrorDecoder;
import com.myreflectionthoughts.user.datamodel.request.LoginRequest;
import com.myreflectionthoughts.user.datamodel.request.RegistrationRequest;
import com.myreflectionthoughts.user.datamodel.response.RegistrationResponse;
import feign.Response;
import org.springframework.stereotype.Component;

@Component
public class AuthAdapter implements AuthAdapterInterface{

    private final AuthClient authClient;

    public AuthAdapter(AuthClient authClient){
        this.authClient = authClient;
    }

    @Override
    public RegistrationResponse registerUser(RegistrationRequest registrationRequest) {
        return authClient.registerUser(registrationRequest);
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        return authClient.loginUser(loginRequest);
    }
}
