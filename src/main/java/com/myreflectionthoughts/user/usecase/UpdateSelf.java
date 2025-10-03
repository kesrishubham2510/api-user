package com.myreflectionthoughts.user.usecase;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UpdateSelf<T, P> {
    ResponseEntity<T> updateMyData(P p, HttpServletRequest servletRequest);
}
