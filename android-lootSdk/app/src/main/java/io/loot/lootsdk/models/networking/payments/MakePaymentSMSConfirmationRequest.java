package io.loot.lootsdk.models.networking.payments;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MakePaymentSMSConfirmationRequest {
    @SerializedName("code")
    private String code;

    public MakePaymentSMSConfirmationRequest(String code) {
        this.code = code;
    }

    public MakePaymentSMSConfirmationRequest() {}
}
