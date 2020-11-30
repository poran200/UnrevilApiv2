
package com.unriviel.api.model.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.NotBlank;

@Tag(name = "Password reset link request", description = "The password reset link payload")
public class PasswordResetLinkRequest {

    @NotBlank(message = "Email cannot be blank")
    @Schema(name = "User registered email", required = true, allowableValues = "NonEmpty String")
    private String email;

    public PasswordResetLinkRequest(String email) {
        this.email = email;
    }

    public PasswordResetLinkRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
