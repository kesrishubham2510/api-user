package com.myreflectionthoughts.user.usecase;

import org.springframework.http.ResponseEntity;

public interface ChangeUserDesignation<T> {

    public ResponseEntity<T> updateUser(String src, String userId);
}
