package io.loot.lootsdk.models.networking.sessions;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class LoginResponse {

    @SerializedName("email")
    private String email;

    @SerializedName("token")
    private String token;

    @SerializedName("email_verified")
    private int emailVerified;

    @SerializedName("intercom_hash")
    private String intercomHash;

    @SerializedName("public_id")
    private String publicId;
}
