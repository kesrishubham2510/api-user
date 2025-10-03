package com.myreflectionthoughts.user.handler;

import com.myreflectionthoughts.user.exception.AuthServiceException;
import com.myreflectionthoughts.user.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Map<String, String>> handleUserException(UserException ex) {
        Map<String, String> body = Map.of("key", ex.getKey(), "message", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @ExceptionHandler(value = AuthServiceException.class)
    public ResponseEntity<Map<String, String>> buildExceptionMessage(AuthServiceException authServiceException){
        Map<String, String> body = Map.of("message", authServiceException.getMessage(), "error", authServiceException.getError(), "nextStep", authServiceException.getNextStep());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Map<String, String>> handleUnSupportedException(UnsupportedOperationException ex) {
        Map<String, String> body = Map.of("key", "NOT_SUPPORTED", "message", ex.getMessage());
        return ResponseEntity
                .status(501)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }


}
