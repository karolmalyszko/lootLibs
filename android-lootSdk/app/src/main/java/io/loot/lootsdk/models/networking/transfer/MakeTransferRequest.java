package io.loot.lootsdk.models.networking.transfer;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
@Deprecated
@Data
public class MakeTransferRequest {
    @SerializedName("recipient_name")
    String recipentName;
    @SerializedName("recipient_account_number")
    String recipentAccountNumber;
    @SerializedName("recipient_sort_code")
    String recipentSortCode;
    @SerializedName("amount")
    int amount;
    @SerializedName("reference")
    String reference;

    public MakeTransferRequest(String recipentName, String recipentAccountNumber, String recipentSortCode, int amount, String reference) {
        this.recipentName = recipentName;
        this.recipentAccountNumber = recipentAccountNumber;
        this.recipentSortCode = recipentSortCode;
        this.amount = amount;
        this.reference = reference;
    }

}
