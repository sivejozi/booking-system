package com.sive.bookingsystem.exception.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Provided token is invalid")
public class ResetCodeInvalidException extends RuntimeException {

    private String token;
    public ResetCodeInvalidException(String token) {
        super("Code : " + token + " is not valid");
        this.token = token;
    }
}