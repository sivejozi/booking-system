package com.sive.bookingsystem.exception.appointment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No appointment found with id provided")
public class AppointmentNotFoundException extends RuntimeException {

    private Long id;

    public AppointmentNotFoundException(Long id) {
        super("No appointment found with id: " + id);
        this.id = id;
    }
}