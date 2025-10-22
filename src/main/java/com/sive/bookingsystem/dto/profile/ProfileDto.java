package com.sive.bookingsystem.dto.profile;

import lombok.Data;

import java.util.Set;

@Data
public class ProfileDto {
    private String title, name, surname, email, cellphone, password, confirmPassword;
    private Set<RoleDto> roles;

    public ProfileDto(String title, String name, String surname, String email, String cellphone, String password, String confirmPassword,
                      Set<RoleDto> roles) {
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.cellphone = cellphone;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roles = roles;
    }

    public ProfileDto() {
    }
}
