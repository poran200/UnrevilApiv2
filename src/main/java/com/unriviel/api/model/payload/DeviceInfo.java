package com.unriviel.api.model.payload;

import com.unriviel.api.model.DeviceType;

public class DeviceInfo {



    private String deviceId;


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
