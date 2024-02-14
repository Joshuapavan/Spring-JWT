package com.joshuapavan.jwtpractice.services;

import com.joshuapavan.jwtpractice.dto.JwtAuthenticationResponse;
import com.joshuapavan.jwtpractice.dto.RefreshTokenRequest;
import com.joshuapavan.jwtpractice.dto.SignInRequest;
import com.joshuapavan.jwtpractice.dto.SignUpRequest;
import com.joshuapavan.jwtpractice.entities.User;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse logIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
