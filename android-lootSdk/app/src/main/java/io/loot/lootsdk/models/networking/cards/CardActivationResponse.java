package io.loot.lootsdk.models.networking.cards;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.cards.CardActivation;
import lombok.Data;

@Data
public class CardActivationResponse implements Serializable {

    @SerializedName("id")
    String id;

    @SerializedName("account_id")
    String accountId;

    @SerializedName("status")
    String status;

    @SerializedName("pin")
    String pinCode;

    public static CardActivation parseToDataObject(CardActivationResponse cardActivationResponse) {
        if (cardActivationResponse == null) {
            cardActivationResponse = new CardActivationResponse();
        }
        CardActivation cardActivation = new CardActivation();

        cardActivation.setId(cardActivationResponse.getId());
        cardActivation.setAccountId(cardActivationResponse.getAccountId());
        cardActivation.setStatus(cardActivationResponse.getStatus());
        cardActivation.setPinCode(cardActivationResponse.getPinCode());

        return cardActivation;
    }
}
