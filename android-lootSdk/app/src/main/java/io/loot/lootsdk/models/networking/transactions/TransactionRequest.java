package io.loot.lootsdk.models.networking.transactions;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class TransactionRequest {

    @SerializedName("from_date")
    private String fromDate; // YYYYMMDDHHmmss (24 hour time format)

    @SerializedName("to_date")
    private String toDate; // YYYYMMDDHHmmss (24 hour time format)

    @SerializedName("last_n_transactions")
    private int lastNTransactions;

    @SerializedName("transaction_amount")
    private int transactionAmount;

    @SerializedName("contis_transaction_type_code")
    private String contisTransactionTypeCode;

}
