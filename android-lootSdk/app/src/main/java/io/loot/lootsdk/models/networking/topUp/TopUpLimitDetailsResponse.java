package io.loot.lootsdk.models.networking.topUp;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TopUpLimitDetailsResponse {

    @SerializedName("charges")
    int charges;

    @SerializedName("amount")
    String amount;

}
