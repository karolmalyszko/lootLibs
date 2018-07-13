package io.loot.lootsdk.models.networking.payments;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.transfer.Transfer;
import lombok.Data;

@Data
public class CreateManualDetailsPaymentRequest {
    @SerializedName("amount")
    private Integer amount;
    @SerializedName("reference")
    private String reference;
    @SerializedName("recipient_account_number")
    private String recipientAccountNumber;
    @SerializedName("recipient_sort_code")
    private String recipientSortCode;
    @SerializedName("recipient_name")
    private String recipientName;
    @SerializedName("touch_id_token")
    private String touchIdToken;
    @SerializedName("password")
    private String password;

    public CreateManualDetailsPaymentRequest(){

    };

    public CreateManualDetailsPaymentRequest(Transfer transfer) {
        if(transfer == null) {
            transfer = new Transfer();
        }
        this.setRecipientName(transfer.getRecipentName());
        this.setRecipientAccountNumber(transfer.getRecipentAccountNumber());
        this.setRecipientSortCode(transfer.getRecipentSortCode());
        this.setAmount(transfer.getAmount());
        this.setReference(transfer.getReference());
    }
}
