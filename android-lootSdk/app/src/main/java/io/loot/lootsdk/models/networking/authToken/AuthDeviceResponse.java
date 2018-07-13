package io.loot.lootsdk.models.networking.authToken;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.authToken.AuthorizedDevice;
import io.loot.lootsdk.models.orm.AuthDeviceEntity;
import lombok.Data;

@Data
public class AuthDeviceResponse implements Serializable {

    @SerializedName("device_name")
    String deviceName;
    @SerializedName("device_id")
    String deviceId;

    public AuthDeviceResponse(String deviceName, String deviceId) {
        this.deviceName = deviceName;
        this.deviceId = deviceId;
    }

    public static AuthDeviceEntity parseToEntityObject(AuthDeviceResponse authDeviceResponse) {
        if (authDeviceResponse == null) {
            authDeviceResponse = new AuthDeviceResponse("", "");
        }
        AuthDeviceEntity entity = new AuthDeviceEntity();

        entity.setDeviceId(authDeviceResponse.getDeviceId());
        entity.setDeviceName(authDeviceResponse.getDeviceName());

        return entity;
    }

    public static AuthorizedDevice parseToDataObject(AuthDeviceResponse authDeviceResponse) {
        if (authDeviceResponse == null) {
            authDeviceResponse = new AuthDeviceResponse("", "");
        }
        AuthorizedDevice authorizedDevice = new AuthorizedDevice();

        authorizedDevice.setDeviceId(authDeviceResponse.getDeviceId());
        authorizedDevice.setDeviceName(authDeviceResponse.getDeviceName());

        return authorizedDevice;
    }

}
