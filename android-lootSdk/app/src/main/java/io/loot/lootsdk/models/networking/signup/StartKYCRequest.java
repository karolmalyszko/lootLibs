package io.loot.lootsdk.models.networking.signup;

import com.google.gson.annotations.SerializedName;

public class StartKYCRequest {

    @SerializedName("reference_id")
    String referenceId;

    public StartKYCRequest(String referenceId) {
        this.referenceId = referenceId;
    }

}
