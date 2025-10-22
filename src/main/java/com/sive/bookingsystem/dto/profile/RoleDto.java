package com.sive.bookingsystem.dto.profile;

import lombok.Data;
import org.apache.commons.lang3.builder.CompareToBuilder;

@Data
public class RoleDto implements Comparable<RoleDto> {
    private String name;

    public RoleDto(String name) {
        this.name = name;
    }

    public RoleDto() {
    }

    @Override
    public int compareTo(RoleDto roleDto) {
        return new CompareToBuilder().append(name, roleDto.name).toComparison();
    }
}
