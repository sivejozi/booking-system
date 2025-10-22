package com.sive.bookingsystem.model.security;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@ToString
@Data
@Entity
@Table(name = "users", schema = "public")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String name;

    private String surname;

    private String cellphone;

    private String email;

    private String password;

    private String confirmPassword;

    private String verification;

    @Column(name = "type", unique = true)
    @Enumerated(EnumType.STRING)
    private transient UserType type;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", schema = "public",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roles;

    public UserModel(Long id, String title, String name, String surname, String cellphone, String email, String password,
                     String confirmPassword, String verification, UserType type, Set<RoleModel> roles) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.cellphone = cellphone;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.verification = verification;
        this.type = type;
        this.roles = roles;
    }

    public UserModel() {
    }
}
