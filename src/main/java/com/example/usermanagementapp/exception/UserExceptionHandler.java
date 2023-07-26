package com.example.usermanagementapp.exception;

import com.example.usermanagementapp.model.UserErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        UserErrorResponse userErrorResponse = new UserErrorResponse();

        userErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        userErrorResponse.setMessage(ex.getMessage());
        userErrorResponse.setTimeStamp(System.currentTimeMillis());

        return ResponseEntity.status(404).body(userErrorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(Exception ex) {
        UserErrorResponse userErrorResponse = new UserErrorResponse();

        userErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        userErrorResponse.setMessage(ex.getMessage());
        userErrorResponse.setTimeStamp(System.currentTimeMillis());

        return ResponseEntity.status(400).body(userErrorResponse);
    }
}
