package com.parth.billingsoftware.controller;

import com.parth.billingsoftware.dto.LoginRequest;
import com.parth.billingsoftware.io.AuthRequest;
import com.parth.billingsoftware.io.AuthResponse;
import com.parth.billingsoftware.service.UserService;
import com.parth.billingsoftware.service.impl.AppUserDetailService;
import com.parth.billingsoftware.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailService appUserDetailsService;
    private final JwtUtil jwtUtill;
    private final UserService userService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) throws Exception {
        authenticate(request.getEmail(), request.getPassword());
        final UserDetails userDetails = appUserDetailsService.loadUserByUsername(request.getEmail());
        final String jwtToken = jwtUtill.generateToken(userDetails);
        String role = userService.getUserRole(request.getEmail());
        return AuthResponse.builder()
                .token(jwtToken)
                .role(role)
                .email(request.getEmail())
                .build();
    }

    private void authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        } catch (DisabledException e) {
            // Consider a more specific exception for disabled users
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User account is disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }
    @PostMapping("/encode")
    public String encodePassword(@RequestBody Map<String, String> request){
      return passwordEncoder.encode(request.get("password"));
     }
}