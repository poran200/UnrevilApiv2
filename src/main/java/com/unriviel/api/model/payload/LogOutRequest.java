package com.unriviel.api.model.payload;


import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Schema(name = "Logout request", description = "The logout request payload")
public class LogOutRequest {

    @Valid
    @NotNull(message = "Device info cannot be null")
    @Schema(description = "Device info", required = true, type = "object", allowableValues = "A valid " +
            "deviceInfo object")
    private DeviceInfo deviceInfo;

    public LogOutRequest() {
    }

    public LogOutRequest(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
