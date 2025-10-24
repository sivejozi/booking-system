package com.sive.bookingsystem.controller.security;

import com.sive.bookingsystem.dto.profile.ProfileDTO;
import com.sive.bookingsystem.dto.profile.ProfileSummaryDTO;
import com.sive.bookingsystem.dto.profile.RoleDTO;
import com.sive.bookingsystem.dto.security.response.security.AuthenticationResponse;
import com.sive.bookingsystem.dto.security.response.security.JwtResponse;
import com.sive.bookingsystem.exception.security.UserNotFoundException;
import com.sive.bookingsystem.exception.security.UserRegistrationFailureException;
import com.sive.bookingsystem.model.repository.security.UserRepository;
import com.sive.bookingsystem.model.security.RoleModel;
import com.sive.bookingsystem.model.security.UserModel;
import com.sive.bookingsystem.service.profile.security.AuthenticationDetailsService;
import com.sive.bookingsystem.service.profile.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationDetailsService userDetailsService;

    @Autowired
    private UserRepository profileRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    Logger logger = Logger.getLogger(AuthenticationController.class.getName());

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody ProfileSummaryDTO authenticationRequest) throws Exception {

        // Authenticate the user. If user credentials are invalid, this will throw an exception
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // If authentication is successful, generate a JWT for the user
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtService.generateToken(userDetails.getUsername());
        Optional<UserModel> userOptional = profileRepository.findByEmailIgnoreCase(authenticationRequest.getEmail());
        if (userOptional.isPresent()) {
            AuthenticationResponse authenticationResponse = getAuthenticationResponse(userOptional.get(), jwt);
            return ResponseEntity.ok(authenticationResponse);
        }
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping(value = "/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> registerProfile(@RequestBody ProfileDTO profileDto) {
        logger.info("Registering new user ...");
        String email = profileDto.getName();

        if (profileDto.getEmail() != null && profileDto.getPassword() != null && (profileDto.getPassword().equals(profileDto.getConfirmPassword()))) {
            ProfileDTO newProfileDto = userDetailsService.registerUser(profileDto);
            if (newProfileDto != null) {
                return ResponseEntity.ok(newProfileDto);
            } else {
                throw new UserRegistrationFailureException(email);
            }
        } else {
            throw new UserNotFoundException(email);
        }
    }

    private AuthenticationResponse getAuthenticationResponse(UserModel userModel, String jwt) {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        authenticationResponse.setName(userModel.getName());
        authenticationResponse.setSurname(userModel.getSurname());
        authenticationResponse.setCellphone(userModel.getCellphone());
        authenticationResponse.setEmail(userModel.getEmail());
        authenticationResponse.setTitle(userModel.getTitle());
        authenticationResponse.setPassword(userModel.getPassword());
        authenticationResponse.setConfirmPassword(userModel.getConfirmPassword());
        authenticationResponse.setRoles(convertRoles(userModel.getRoles()));
        return authenticationResponse;
    }

    public Set<RoleDTO> convertRoles(Set<RoleModel> rolesDb) {
        Set<RoleDTO> roles = new HashSet<>();
        for (RoleModel role : rolesDb) {
            if (role != null) {
                roles.add(convertRoleEntityToDtoRole(role));
            }
        }
        return roles;
    }

    private RoleDTO convertRoleEntityToDtoRole(RoleModel roleModel) {
        return modelMapper.map(roleModel, RoleDTO.class);
    }
}
