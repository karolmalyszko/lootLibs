package io.loot.lootsdk.models.networking.waitingList;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class SignUpWaitingListRequest {

    @SerializedName("email")
    private String email;
    @SerializedName("referral_code")
    private String referralCode;

    public SignUpWaitingListRequest(String email, String referralCode) {
        this.email = email;
        this.referralCode = referralCode;
    }

}
