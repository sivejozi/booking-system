package com.sive.bookingsystem.controller.security;

import com.sive.bookingsystem.dto.profile.ProfileDto;
import com.sive.bookingsystem.dto.profile.ProfileSummaryDto;
import com.sive.bookingsystem.dto.profile.RoleDto;
import com.sive.bookingsystem.dto.security.response.security.AuthenticationResponse;
import com.sive.bookingsystem.dto.security.response.security.JwtResponse;
import com.sive.bookingsystem.exception.security.ResetCodeExpiredException;
import com.sive.bookingsystem.exception.security.ResetCodeInvalidException;
import com.sive.bookingsystem.exception.security.UserNotFoundException;
import com.sive.bookingsystem.exception.security.UserRegistrationFailureException;
import com.sive.bookingsystem.model.repository.security.UserRepository;
import com.sive.bookingsystem.model.security.RoleModel;
import com.sive.bookingsystem.model.security.UserModel;
import com.sive.bookingsystem.service.profile.security.AuthenticationDetailsService;
import com.sive.bookingsystem.service.profile.security.JwtService;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody ProfileSummaryDto authenticationRequest) throws Exception {

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

    public Set<RoleDto> convertRoles(Set<RoleModel> rolesdb) {
        Set<RoleDto> roles = new HashSet<>();
        for (RoleModel role : rolesdb) {
            if (role != null) {
                roles.add(convertRoleEntityToDtoRole(role));
            }
        }
        return roles;
    }

    private RoleDto convertRoleEntityToDtoRole(RoleModel roleModel) {
        return modelMapper.map(roleModel, RoleDto.class);
    }

    @PostMapping(value = "/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> registerProfile(@RequestBody ProfileDto profileDto) {
        logger.info("Registering new user ...");
        String email = profileDto.getName();

        if (profileDto.getEmail() != null && profileDto.getPassword() != null && (profileDto.getPassword().equals(profileDto.getConfirmPassword()))) {
            ProfileDto newProfileDto = userDetailsService.registerUser(profileDto);
            if (newProfileDto != null) {
                return ResponseEntity.ok(newProfileDto);
            } else {
                throw new UserRegistrationFailureException(email);
            }
        } else {
            throw new UserNotFoundException(email);
        }
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleUserNotFound(UserNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(UserRegistrationFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handlerRegistrationFailure(UserRegistrationFailureException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(ResetCodeInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handlerInvalidCodeFailure(ResetCodeInvalidException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(ResetCodeExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handlerExpiredCodeFailure(ResetCodeExpiredException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
