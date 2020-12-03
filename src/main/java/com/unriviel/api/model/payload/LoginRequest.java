package com.unriviel.api.model.payload;

import com.unriviel.api.validation.annotation.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.NotNull;

@Tag(name ="Log in request ", description = "Login Request")
public class LoginRequest {

    @NullOrNotBlank(message = "Login Username can be null but not blank")
    private String userEmail;

    @NotNull(message = "Login password cannot be blank")
    @Schema(description = "Valid user password", required = true, allowableValues = "NonEmpty String")
    private String password;

    private DeviceInfo deviceInfo;

    public LoginRequest(String userEmail, String password, DeviceInfo deviceInfo) {
        this.userEmail = userEmail;
        this.password = password;
        this.deviceInfo = deviceInfo;
    }

    public LoginRequest() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
