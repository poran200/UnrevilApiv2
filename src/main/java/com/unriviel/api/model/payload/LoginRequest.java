package com.unriviel.api.model.payload;

import com.unriviel.api.validation.annotation.NullOrNotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Tag(name ="Log in request ", description = "Login Request")
public class LoginRequest {

    @NullOrNotBlank(message = "Login Username can be null but not blank")
    @Schema(title = "Registered username", allowableValues = "NonEmpty String")
    private String username;
//
//    @NullOrNotBlank(message = "Login Email can be null but not blank")
//    @Schema(description = "User registered email", required = true, allowableValues = "NonEmpty String")
//    private String email;

    @NotNull(message = "Login password cannot be blank")
    @Schema(description = "Valid user password", required = true, allowableValues = "NonEmpty String")
    private String password;

    @Valid
    @NotNull(message = "Device info cannot be null")
    @Schema(description = "Device info", required = true, type = "object", allowableValues = "A valid " +
            "deviceInfo object")
    private DeviceInfo deviceInfo;

    public LoginRequest(String username, String password, DeviceInfo deviceInfo) {
        this.username = username;
        this.password = password;
        this.deviceInfo = deviceInfo;
    }

    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
