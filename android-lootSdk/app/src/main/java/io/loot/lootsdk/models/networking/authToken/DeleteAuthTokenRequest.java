package io.loot.lootsdk.models.networking.authToken;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class DeleteAuthTokenRequest {

    @SerializedName("device_id")
    String deviceID;

    public DeleteAuthTokenRequest(String deviceID) {
        this.deviceID = deviceID;
    }

}
