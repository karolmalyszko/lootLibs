package io.loot.lootsdk.models.networking.topUp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.topUp.TopUpResult;
import lombok.Data;

@Data
public class TopUpResponse implements Serializable {
    @SerializedName("redirect_url")
    String redirectUrl;

    @SerializedName("required_3ds")
    boolean required3ds;

    public static TopUpResult parseToDataObject(TopUpResponse topUpResponse) {
        if (topUpResponse == null) {
            topUpResponse = new TopUpResponse();
        }
        TopUpResult topUpResult = new TopUpResult();

        topUpResult.setRedirectUrl(topUpResponse.getRedirectUrl());
        topUpResult.setRequired3ds(topUpResponse.isRequired3ds());

        return topUpResult;
    }
}
