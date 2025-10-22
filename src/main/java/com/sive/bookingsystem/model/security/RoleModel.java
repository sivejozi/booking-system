package com.sive.bookingsystem.model.security;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Set;

@Data
@Entity
@Table(name = "roles", schema="public")
public class RoleModel implements Comparable<RoleModel>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserModel> users;

    public RoleModel(Long id, String name, Set<UserModel> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public RoleModel() {
    }

    @Override
    public int compareTo(RoleModel roleModel) {
        return new CompareToBuilder().append(name, roleModel.name).toComparison();
    }
}
