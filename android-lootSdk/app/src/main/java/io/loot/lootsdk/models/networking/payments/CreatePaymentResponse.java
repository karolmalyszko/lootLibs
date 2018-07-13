package io.loot.lootsdk.models.networking.payments;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.payments.ManualDetailsPayment;
import lombok.Data;

@Data
public class CreatePaymentResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("requires_2fa")
    private Boolean requires2fa;

    public static ManualDetailsPayment parseToDataObject(CreatePaymentResponse createPaymentResponse) {
        if (createPaymentResponse == null) {
            createPaymentResponse = new CreatePaymentResponse();
        }
        ManualDetailsPayment manualDetailsPayment = new ManualDetailsPayment();
        manualDetailsPayment.setId(createPaymentResponse.getId());
        manualDetailsPayment.setRequires2fa(createPaymentResponse.getRequires2fa());
        return manualDetailsPayment;
    }
}
