package io.loot.lootsdk.models.networking.waitingList;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class WaitingListResendEmailRequest {

    @SerializedName("email")
    String email;

    public WaitingListResendEmailRequest(String email) {
        this.email = email;
    }
}
