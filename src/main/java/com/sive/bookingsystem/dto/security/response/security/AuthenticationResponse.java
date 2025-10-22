package com.sive.bookingsystem.dto.security.response.security;


import com.sive.bookingsystem.dto.profile.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String title, name, surname, email, cellphone, password, confirmPassword;
    private Set<RoleDto> roles;
}