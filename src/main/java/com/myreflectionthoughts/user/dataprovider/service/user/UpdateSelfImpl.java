package com.myreflectionthoughts.user.dataprovider.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myreflectionthoughts.user.datamodel.entity.User;
import com.myreflectionthoughts.user.datamodel.request.LoginRequest;
import com.myreflectionthoughts.user.datamodel.request.UpdateSelfRequest;
import com.myreflectionthoughts.user.datamodel.response.LoginResponse;
import com.myreflectionthoughts.user.datamodel.response.UserDetails;
import com.myreflectionthoughts.user.dataprovider.repository.UserRepository;
import com.myreflectionthoughts.user.exception.UserException;
import com.myreflectionthoughts.user.usecase.UpdateSelf;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UpdateSelfImpl implements UpdateSelf<UserDetails, UpdateSelfRequest> {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final LoginUserImpl loginUserImpl;

    public UpdateSelfImpl(ObjectMapper objectMapper,
                          UserRepository userRepository,
                          LoginUserImpl loginUserImpl){
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.loginUserImpl = loginUserImpl;
    }

    @Override
    public ResponseEntity<UserDetails> updateMyData(UpdateSelfRequest updateSelfRequest, HttpServletRequest servletRequest) {

        boolean usernameChanged = false;

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(StringUtils.isNotEmpty(updateSelfRequest.getEmail())){
            checkEmailUniqueness(updateSelfRequest.getEmail());
            user.setEmail(updateSelfRequest.getEmail());
        }

        if(StringUtils.isNotEmpty(updateSelfRequest.getUsername())){
            checkUsernameUniqueness(updateSelfRequest.getUsername());
            user.setUsername(updateSelfRequest.getUsername());
            // need to login the user again
            usernameChanged = true;
        }

        if(StringUtils.isNotEmpty(updateSelfRequest.getFirstName())){
            user.setFirstName(updateSelfRequest.getFirstName());
        }

        if(StringUtils.isNotEmpty(updateSelfRequest.getLastName())){
            user.setLastName(updateSelfRequest.getLastName());
        }

        User updatedUser = userRepository.save(user);
        UserDetails userDetails = objectMapper.convertValue(updatedUser, UserDetails.class);

        HttpHeaders headers = new HttpHeaders();

        if(usernameChanged){
            userDetails.setNextStep("Please login again to continue");
        }else{
            String jwtToken = servletRequest.getHeader("Authorization");
            headers.add("Authorization", "Bearer "+jwtToken);
        }

/*
        Can be enabled, when every request is treated as Transaction, else user might loose
        access to account if he/she forgot the new username/email
 */


//        if(usernameChanged){
//            LoginRequest loginRequest  = new LoginRequest();
//
//            loginRequest.setUsername(updateSelfRequest.getUsername());
//            loginRequest.setPassword(user.getPassword());
//
//            ResponseEntity<LoginResponse> loginResponse = loginUserImpl.loginUser(loginRequest);
//
//            loginResponse.getHeaders().forEach((key, value) -> {
//                if (value.stream().findFirst().stream().findFirst().isPresent()) {
//                    headers.add(key, value.stream().findFirst().stream().findFirst().get());
//                } else {
//                    System.out.println("Key:- " + key + ", is empty");
//                }
//            });
//
//        }


        return ResponseEntity.status(200).headers(headers).body(userDetails);
    }

    private void checkEmailUniqueness(String email){
        if(!Objects.isNull(userRepository.findByEmail(email))){
            throw new UserException("The email is already taken, please use different one");
        }
    }

    private void checkUsernameUniqueness(String username){
        if(!Objects.isNull(userRepository.findByUsername(username))){
            throw new UserException("The username is already taken, please use different one");
        }
    }
}
