package io.loot.lootsdk.models.networking.transactions;


import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class IncludeExcludeResponse {

    @SerializedName("transaction")
    private TransactionResponse transaction;
}
