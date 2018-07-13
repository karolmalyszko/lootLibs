package io.loot.lootsdk.models.networking.authToken;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import io.loot.lootsdk.models.data.authToken.AuthorizedDevice;
import io.loot.lootsdk.models.orm.AuthDeviceEntity;
import lombok.Data;

@Data
public class AuthDevicesResponse implements Serializable {

    @SerializedName("devices")
    ArrayList<AuthDeviceResponse> authDevices;

    public static ArrayList<AuthDeviceEntity> parseToEntityObject(AuthDevicesResponse authDevicesResponse) {
        if (authDevicesResponse == null) {
            authDevicesResponse = new AuthDevicesResponse();
        }
        if (authDevicesResponse.getAuthDevices() == null) {
            authDevicesResponse.setAuthDevices(new ArrayList<AuthDeviceResponse>());
        }
        ArrayList<AuthDeviceEntity> authDeviceEntities = new ArrayList<AuthDeviceEntity>();
        for (AuthDeviceResponse authDeviceResponse : authDevicesResponse.getAuthDevices()) {
            authDeviceEntities.add(AuthDeviceResponse.parseToEntityObject(authDeviceResponse));
        }
        return authDeviceEntities;
    }

    public static ArrayList<AuthorizedDevice>  parseToDataObject(AuthDevicesResponse authDevicesResponse) {
        if (authDevicesResponse == null) {
            authDevicesResponse = new AuthDevicesResponse();
        }
        if (authDevicesResponse.getAuthDevices() == null) {
            authDevicesResponse.setAuthDevices(new ArrayList<AuthDeviceResponse>());
        }
        ArrayList<AuthorizedDevice> authorizedDeviceObjects = new ArrayList<AuthorizedDevice>();
        for (AuthDeviceResponse authDeviceResponse : authDevicesResponse.getAuthDevices()) {
            authorizedDeviceObjects.add(AuthDeviceResponse.parseToDataObject(authDeviceResponse));
        }
        return authorizedDeviceObjects;
    }

}
