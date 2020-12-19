package com.unriviel.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class InviteEmailDto {
    @NotEmpty(message = "email not empty ")
    @Email
    private String email;
}
