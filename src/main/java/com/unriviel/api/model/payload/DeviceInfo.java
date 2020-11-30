package com.unriviel.api.model.payload;

import com.unriviel.api.model.DeviceType;
import com.unriviel.api.validation.annotation.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DeviceInfo {

    @NotBlank(message = "Device id cannot be blank")
    @Schema(description = "Device Id", required = true, type = "string", allowableValues = "Non empty string")
    private String deviceId;

    @NotNull(message = "Device type cannot be null")
    @Schema(description = "Device type Android/iOS", required = true, type = "string", allowableValues =
            "DEVICE_TYPE_ANDROID, DEVICE_TYPE_IOS,WEB")
    private DeviceType deviceType;


    public DeviceInfo() {
    }

    public DeviceInfo(String deviceId, DeviceType deviceType) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceType=" + deviceType +
                '}';
    }
}
