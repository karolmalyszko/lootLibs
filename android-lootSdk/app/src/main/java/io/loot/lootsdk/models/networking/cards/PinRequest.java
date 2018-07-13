package io.loot.lootsdk.models.networking.cards;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class PinRequest implements Serializable {

    @SerializedName("code")
    String code;

    @SerializedName("pan")
    String panNumber;

    public PinRequest(String code, String panNumber) {
        this.code = code;
        this.panNumber = panNumber;
    }

}
