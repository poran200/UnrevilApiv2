package com.unriviel.api.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class InviteEmailDto {
    @NotEmpty(message = "email not empty ")
    private String email;
}
