package com.unriviel.api.dto;

import com.unriviel.api.validation.annotation.NullOrNotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LoginDto {
    @NullOrNotBlank(message = "Login Username or Email can be null but not blank")
   private String userNameOrEmail;
    @NotNull(message = "Login password cannot be blank")
   private String password;
}
