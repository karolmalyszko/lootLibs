package io.loot.lootsdk.models.networking.payments;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.transfer.Transfer;
import lombok.Data;

@Data
public class MakePaymentRequest {
    @SerializedName("recipient_name")
    private String recipientName;
    @SerializedName("amount")
    private Integer amount;
    @SerializedName("reference")
    private String reference;
    @SerializedName("touch_id_token")
    private String touchIdToken;
    @SerializedName("password")
    private String password;
    @SerializedName("ui_origin")
    private String uiOrigin;

    public MakePaymentRequest() {
    }

    public MakePaymentRequest(Transfer transfer) {
        if(transfer == null) {
            transfer = new Transfer();
        }
        this.setAmount(transfer.getAmount());
        this.setReference(transfer.getReference());
        this.setRecipientName(transfer.getRecipentName());
        this.setUiOrigin(transfer.getUiOrigin());
    }
}
