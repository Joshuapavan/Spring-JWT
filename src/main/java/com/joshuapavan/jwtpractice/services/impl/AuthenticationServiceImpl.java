package com.joshuapavan.jwtpractice.services.impl;

import com.joshuapavan.jwtpractice.dto.JwtAuthenticationResponse;
import com.joshuapavan.jwtpractice.dto.RefreshTokenRequest;
import com.joshuapavan.jwtpractice.dto.SignInRequest;
import com.joshuapavan.jwtpractice.dto.SignUpRequest;
import com.joshuapavan.jwtpractice.entities.User;
import com.joshuapavan.jwtpractice.enums.Role;
import com.joshuapavan.jwtpractice.repos.UserRepository;
import com.joshuapavan.jwtpractice.services.AuthenticationService;
import com.joshuapavan.jwtpractice.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;


    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest signUpRequest){
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new JwtAuthenticationResponse(jwt,refreshToken);
    }


    @Override
    public JwtAuthenticationResponse logIn(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s, not found", signInRequest.getEmail())));

        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new JwtAuthenticationResponse(jwt,refreshToken);
    }


    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String email = jwtService.extractUserName(refreshTokenRequest.getRefreshToken());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s, not found", email)));

        if(jwtService.isTokenValid(refreshTokenRequest.getRefreshToken(), user)){
            String jwt = jwtService.generateToken(user);
            String refreshToken = refreshTokenRequest.getRefreshToken();
            return new JwtAuthenticationResponse(jwt, refreshToken);


        }
        return null;
    }
}
