package com.joshuapavan.jwtpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RefreshTokenRequest {
    private String refreshToken;
}
