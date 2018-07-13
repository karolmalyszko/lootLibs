package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import io.loot.lootsdk.models.data.authToken.AuthorizedDevice;

@android.arch.persistence.room.Entity(tableName = "AuthDevice")
public class AuthDeviceEntity {

    @PrimaryKey
    @NonNull
    private String deviceId = "0";
    private String deviceName;

    public AuthorizedDevice parseToDataObject() {
        AuthorizedDevice authorizedDevice = new AuthorizedDevice();

        authorizedDevice.setDeviceId(deviceId);
        authorizedDevice.setDeviceName(deviceName);

        return authorizedDevice;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
