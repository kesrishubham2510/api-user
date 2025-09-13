package com.myreflectionthoughts.user.adapter.adapter;

import com.myreflectionthoughts.user.datamodel.request.LoginRequest;
import com.myreflectionthoughts.user.datamodel.request.RegistrationRequest;
import com.myreflectionthoughts.user.datamodel.response.LoginResponse;
import com.myreflectionthoughts.user.datamodel.response.RegistrationResponse;

public interface AuthAdapterInterface {
    RegistrationResponse registerUser(RegistrationRequest registrationRequest);
    LoginResponse loginUser(LoginRequest loginRequest);
}
