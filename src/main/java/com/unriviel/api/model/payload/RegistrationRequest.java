package com.unriviel.api.model.payload;

import com.unriviel.api.validation.annotation.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.NotNull;

@Tag(name = "Registration Request", description = "The registration request payload")
public class RegistrationRequest {

//    @NullOrNotBlank(message = "Registration username can be null but not blank")
//    @Schema(name = "A valid username", allowableValues = "NonEmpty String")
    private String username;

//    @NullOrNotBlank(message = "Registration email can be null but not blank")
//    @Schema(name = "A valid email", required = true, allowableValues = "NonEmpty String")
    private String email;

//    @NotNull(message = "Registration password cannot be null")
//    @Schema(name = "A valid password string", required = true, allowableValues = "NonEmpty String")
    private String password;

//    @NotNull(message = "Specify whether the user has to be registered as an admin or not")
//    @Schema(name = "Flag denoting whether the user is an admin or not", required = true,
//            type = "boolean", allowableValues = "true, false")
    private Boolean registerAsAdmin;

    public RegistrationRequest(String username, String email,
                               String password, Boolean registerAsAdmin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registerAsAdmin = registerAsAdmin;
    }

    public RegistrationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRegisterAsAdmin() {
        return registerAsAdmin;
    }

    public void setRegisterAsAdmin(Boolean registerAsAdmin) {
        this.registerAsAdmin = registerAsAdmin;
    }
}
