package io.loot.lootsdk.models.networking.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

public @Data class SetBudgetRequest implements Serializable {

    @SerializedName("amount")
    String amount;

    public SetBudgetRequest(String amount) {
        this.amount = amount;
    }

}
