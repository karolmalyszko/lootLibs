package io.loot.lootsdk.models.networking.signup;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PhoneNumberRequest {

    @SerializedName("phone_number")
    private String phoneNumber;

    public PhoneNumberRequest(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
