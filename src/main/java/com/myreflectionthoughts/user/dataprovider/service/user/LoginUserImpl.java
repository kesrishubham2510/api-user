package com.myreflectionthoughts.user.dataprovider.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myreflectionthoughts.user.adapter.adapter.AuthAdapter;
import com.myreflectionthoughts.user.adapter.feign.FeignErrorDecoder;
import com.myreflectionthoughts.user.datamodel.request.LoginRequest;
import com.myreflectionthoughts.user.datamodel.response.LoginResponse;
import com.myreflectionthoughts.user.usecase.LoginUser;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class LoginUserImpl implements LoginUser {

    private final AuthAdapter authAdapter;
    private final ObjectMapper objectMapper;
    private final ErrorDecoder feignErrorDecoder;


    public LoginUserImpl(AuthAdapter authAdapter,
                         ObjectMapper objectMapper,
                         ErrorDecoder feignErrorDecoder){
        this.authAdapter = authAdapter;
        this.objectMapper = objectMapper;
        this.feignErrorDecoder = feignErrorDecoder;
    }

    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest) {
        Response authServerResponse = authAdapter.loginUser(loginRequest);

        if(!(authServerResponse.status()>=200 && authServerResponse.status()<300)){
            throw (RuntimeException) feignErrorDecoder.decode("loginUser", authServerResponse);
        }

        LoginResponse loginResponse = null;

        // set the HttpHeaders
        HttpHeaders httpHeaders = new HttpHeaders();

        authServerResponse.headers().forEach((key, value) -> {
            if(value.stream().findFirst().stream().findFirst().isPresent()){
                httpHeaders.add(key, value.stream().findFirst().stream().findFirst().get());
            }else{
                System.out.println("Key:- "+key+", is empty");
            }
        });

        try {
            if(!Objects.isNull(authServerResponse.body())){
                loginResponse = objectMapper.readValue(authServerResponse.body().asInputStream().readAllBytes(), LoginResponse.class);
            }
        }catch (IOException parseException){
            System.out.println("Exception occurred while parsing authServerResponse body");
        }

        return ResponseEntity.status(authServerResponse.status()==201 ? 200 : authServerResponse.status()).headers(httpHeaders).body(loginResponse);
    }
}
