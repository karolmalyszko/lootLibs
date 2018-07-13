package io.loot.lootsdk.models.networking.signup;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class SignupRequest {

    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("phone_number")
    String phoneNumber;
    @SerializedName("waiting_list_token")
    String waitingListToken;

    public SignupRequest(String email, String password, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

}
