package com.sive.bookingsystem.exception.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Provided token is expired")
public class ResetCodeExpiredException extends RuntimeException {

    private String token;
    public ResetCodeExpiredException(String token) {
        super("Code : " + token + " is expired");
        this.token = token;
    }
}