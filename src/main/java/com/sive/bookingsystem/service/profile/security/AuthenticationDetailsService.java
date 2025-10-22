package com.sive.bookingsystem.service.profile.security;

import com.sive.bookingsystem.dto.profile.ProfileDto;
import com.sive.bookingsystem.exception.security.RoleNotFoundException;
import com.sive.bookingsystem.model.repository.security.RoleRepository;
import com.sive.bookingsystem.model.repository.security.UserRepository;
import com.sive.bookingsystem.model.security.RoleModel;
import com.sive.bookingsystem.model.security.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthenticationDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository profileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = profileRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    public ProfileDto registerUser(ProfileDto profileDto) {
        profileDto.setPassword(new BCryptPasswordEncoder().encode(profileDto.getPassword()));
        profileDto.setConfirmPassword(new BCryptPasswordEncoder().encode(profileDto.getConfirmPassword()));
        UserModel userModel = new UserModel();
        userModel.setVerification(generateToken());
        userModel.setEmail(profileDto.getEmail());
        userModel.setName(profileDto.getName());
        userModel.setSurname(profileDto.getSurname());
        userModel.setCellphone(profileDto.getCellphone());
        userModel.setPassword(profileDto.getPassword());
        userModel.setConfirmPassword(profileDto.getConfirmPassword());

        RoleModel userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RoleNotFoundException("ROLE_USER"));

        Set<RoleModel> roles = new HashSet<>();

        if (userRole != null)
            roles.add(userRole);

        userModel.setRoles(roles);
        return modelMapper.map(profileRepository.save(userModel), ProfileDto.class);
    }

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[7];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().encodeToString(bytes);
    }

    public boolean hasExpired(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);
    }

    private static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
