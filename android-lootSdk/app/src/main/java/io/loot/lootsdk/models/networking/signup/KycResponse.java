package io.loot.lootsdk.models.networking.signup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.userinfo.Kyc;
import lombok.Data;

public @Data class KycResponse implements Serializable {

    @SerializedName("status")
    private String status;
    @SerializedName("reason")
    private String reason;
    @SerializedName("details")
    private String details;
    @SerializedName("attempts")
    private int attempts;

    public static Kyc parseToDataObject(KycResponse kycResponse) {
        if (kycResponse == null) {
            kycResponse = new KycResponse();
        }
        Kyc kyc = new Kyc();
        kyc.setStatus(kycResponse.getStatus());
        kyc.setReason(kycResponse.getReason());
        kyc.setDetails(kycResponse.getDetails());
        kyc.setAttempts(kycResponse.getAttempts());
        return kyc;
    }

}
