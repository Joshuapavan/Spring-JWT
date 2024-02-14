package com.joshuapavan.jwtpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
}
