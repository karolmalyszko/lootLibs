package io.loot.lootsdk.models.networking.pushNotifications;

import com.google.gson.annotations.SerializedName;

public class LoginPushNotificationRequest {

    @SerializedName("push_token")
    String token;
    @SerializedName("device_id")
    String deviceId;

    public LoginPushNotificationRequest(String token, String deviceId) {
        this.token = token;
        this.deviceId = deviceId;
    }

}
