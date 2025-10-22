package com.sive.bookingsystem.service.profile;


import com.sive.bookingsystem.dto.profile.ProfileDto;
import com.sive.bookingsystem.dto.profile.RoleDto;

import java.util.List;

public interface ProfileService {
    public ProfileDto createProfile(ProfileDto profileDto);
    public List<ProfileDto> findAll();
    public ProfileDto findByEmail(String email);
    ProfileDto updateProfileRoles(String email, List<RoleDto> roles);
    public List<RoleDto> findRoles(String email);
    public List<RoleDto> findAllRoles();
    boolean existsByEmail(String email);
    public void deleteRole(String email, String name);
    public void deleteUser(String email);
}
