package com.myreflectionthoughts.user.dataprovider.service.user;

import com.myreflectionthoughts.user.adapter.adapter.AuthAdapter;
import com.myreflectionthoughts.user.datamodel.request.RegistrationRequest;
import com.myreflectionthoughts.user.datamodel.response.RegistrationResponse;
import com.myreflectionthoughts.user.usecase.RegisterUser;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserImpl implements RegisterUser {

    private final AuthAdapter authAdapter;

    public RegisterUserImpl(AuthAdapter authAdapter){
        this.authAdapter = authAdapter;
    }

    @Override
    public RegistrationResponse registerUser(RegistrationRequest registrationRequest) {
        return authAdapter.registerUser(registrationRequest);
    }
}
