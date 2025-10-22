package com.sive.bookingsystem.exception.security;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No user found with email provided")
public class UserNotFoundException extends RuntimeException {

    private String email;
    public UserNotFoundException(String email) {
        super("No user found with email: " + email);
        this.email = email;
    }
}