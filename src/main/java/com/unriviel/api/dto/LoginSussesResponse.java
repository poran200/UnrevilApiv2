package com.unriviel.api.dto;

import com.unriviel.api.model.payload.JwtAuthenticationResponse;
import lombok.Data;

@Data
public class LoginSussesResponse {
    UserResponse userResponse;
    JwtAuthenticationResponse jwtAuthenticationResponse;

    public LoginSussesResponse(UserResponse userResponse, JwtAuthenticationResponse jwtAuthenticationResponse) {
        this.userResponse = userResponse;
        this.jwtAuthenticationResponse = jwtAuthenticationResponse;
    }
}
