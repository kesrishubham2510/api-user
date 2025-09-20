package com.myreflectionthoughts.user.usecase;

import com.myreflectionthoughts.user.datamodel.response.UserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ReadSelf {

    ResponseEntity<UserDetails> fetchMyDetails(HttpServletRequest servletRequest);
}
