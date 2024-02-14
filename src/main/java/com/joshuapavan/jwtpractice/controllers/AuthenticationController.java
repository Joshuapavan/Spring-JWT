package com.joshuapavan.jwtpractice.controllers;

import com.joshuapavan.jwtpractice.dto.JwtAuthenticationResponse;
import com.joshuapavan.jwtpractice.dto.RefreshTokenRequest;
import com.joshuapavan.jwtpractice.dto.SignInRequest;
import com.joshuapavan.jwtpractice.dto.SignUpRequest;
import com.joshuapavan.jwtpractice.entities.User;
import com.joshuapavan.jwtpractice.services.AuthenticationService;
import com.joshuapavan.jwtpractice.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signUp (@RequestBody SignUpRequest signUpRequest){
        return new ResponseEntity<>(authenticationService.signUp(signUpRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login (@RequestBody SignInRequest signInRequest){
        return new ResponseEntity<>(authenticationService.logIn(signInRequest), HttpStatus.CREATED);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return new ResponseEntity<>(authenticationService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }



}
