package io.loot.lootsdk.models.networking.contacts;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ContactVerifyRequest {
    @SerializedName("code")
    private String code;

    public ContactVerifyRequest(String code) {
        this.code = code;
    }
}
