package io.loot.lootsdk.models.networking.waitingList;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class WaitingListNotificationTokenRequest {

    @SerializedName("token")
    private String token;
    @SerializedName("notification_token")
    private String notificationToken;
    @SerializedName("device_id")
    private String deviceId;

    public WaitingListNotificationTokenRequest(String token, String notificationToken, String deviceId) {
        this.token = token;
        this.notificationToken = notificationToken;
        this.deviceId = deviceId;
    }
}
