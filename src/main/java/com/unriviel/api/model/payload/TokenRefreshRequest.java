package com.unriviel.api.model.payload;



import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.NotBlank;

@Tag(name = "Token refresh Request", description = "The jwt token refresh request payload")
public class TokenRefreshRequest {

    @NotBlank(message = "Refresh token cannot be blank")
    @Schema(name = "Valid refresh token passed during earlier successful authentications", required = true,
            allowableValues = "NonEmpty String")
    private String refreshToken;

    public TokenRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public TokenRefreshRequest() {
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
