package io.loot.lootsdk.models.networking.topUp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class TopUpRequest implements Serializable {
    @SerializedName("token")
    String token;
    @SerializedName("amount")
    int amount;

    public TopUpRequest(String token, int amount) {
        this.token = token;
        this.amount = amount;
    }

}
