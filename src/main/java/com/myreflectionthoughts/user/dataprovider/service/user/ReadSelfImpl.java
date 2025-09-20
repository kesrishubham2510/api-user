package com.myreflectionthoughts.user.dataprovider.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myreflectionthoughts.user.datamodel.entity.User;
import com.myreflectionthoughts.user.datamodel.entity.UserAuth;
import com.myreflectionthoughts.user.datamodel.response.UserDetails;
import com.myreflectionthoughts.user.usecase.ReadSelf;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReadSelfImpl implements ReadSelf {

    private final ObjectMapper objectMapper;

    public ReadSelfImpl(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<UserDetails> fetchMyDetails(HttpServletRequest servletRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = objectMapper.convertValue(user, UserDetails.class);

        HttpHeaders headers = new HttpHeaders();

        String jwtToken = servletRequest.getHeader("Authorization");
        headers.add("Authorization", jwtToken);

        return ResponseEntity.status(200).headers(headers).body(userDetails);
    }
}
