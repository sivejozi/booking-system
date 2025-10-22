package com.sive.bookingsystem.exception.security;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Role N/A")
public class UserNoRoleException extends RuntimeException {

    public UserNoRoleException(String function) {
        super("Current logged in user does not have permission to update " + function);
    }
}