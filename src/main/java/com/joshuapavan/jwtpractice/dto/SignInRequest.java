package com.joshuapavan.jwtpractice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignInRequest {
    private String email;
    private String password;
}
