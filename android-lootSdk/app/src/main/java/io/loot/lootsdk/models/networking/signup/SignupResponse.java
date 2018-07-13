package io.loot.lootsdk.models.networking.signup;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class SignupResponse {

    @SerializedName("last_finished_step")
    String lastFinishedStep;
    @SerializedName("token")
    String token;
    @SerializedName("intercom_hash")
    String intercomHash;
    @SerializedName("public_id")
    String publicId;
}
