package com.myreflectionthoughts.user.usecase;

import com.myreflectionthoughts.user.datamodel.request.RegistrationRequest;
import com.myreflectionthoughts.user.datamodel.response.RegistrationResponse;

public interface RegisterUser {
    RegistrationResponse registerUser(RegistrationRequest registrationRequest);
}
