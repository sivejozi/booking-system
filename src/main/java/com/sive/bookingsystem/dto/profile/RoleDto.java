package com.sive.bookingsystem.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.CompareToBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements Comparable<RoleDto> {
    private String name;

    @Override
    public int compareTo(RoleDto roleDto) {
        return new CompareToBuilder().append(name, roleDto.name).toComparison();
    }
}
