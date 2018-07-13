package io.loot.lootsdk.models.networking.topUp;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TopUpParsedErrorResponse {

    @SerializedName("error")
    private String error;

    @SerializedName("error_message")
    private String errorMessage;

}
