package io.loot.lootsdk.models.data.authToken;

public class AuthorizedDevice {

    String deviceName;
    String deviceId;

    public AuthorizedDevice() {
        deviceName = "";
        deviceId = "";
    }

    public AuthorizedDevice(AuthorizedDevice authorizedDevice) {
        deviceName = authorizedDevice.getDeviceName();
        deviceId = authorizedDevice.getDeviceId();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        if (deviceId == null) {
            deviceId = "";
        }
        this.deviceId = deviceId;
    }

    public void setDeviceName(String deviceName) {
        if (deviceName == null) {
            deviceName = "";
        }
        this.deviceName = deviceName;
    }
}
