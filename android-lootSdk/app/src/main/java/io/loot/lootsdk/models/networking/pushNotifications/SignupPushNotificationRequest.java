package io.loot.lootsdk.models.networking.pushNotifications;

import com.google.gson.annotations.SerializedName;

public class SignupPushNotificationRequest {

    @SerializedName("token")
    String token;
    @SerializedName("device_id")
    String deviceId;

    public SignupPushNotificationRequest(String token, String deviceId) {
        this.token = token;
        this.deviceId = deviceId;
    }

}
