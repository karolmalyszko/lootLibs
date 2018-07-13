package io.loot.lootsdk.models.networking.topUp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.topUp.TopUpLimits;
import io.loot.lootsdk.models.data.topUp.TopUpResult;
import io.loot.lootsdk.models.orm.TopUpLimitsEntity;
import lombok.Data;

@Data
public class TopUpLimitsResponse implements Serializable {

    @SerializedName("limits")
    TopUpLimitDetailsResponse limits;

    @SerializedName("remaining")
    TopUpLimitDetailsResponse remaining;

    public static TopUpLimits parseToDataObject(TopUpLimitsResponse topUpLimitsResponse) {
        if (topUpLimitsResponse == null) {
            topUpLimitsResponse = new TopUpLimitsResponse();
        }

        if (topUpLimitsResponse.getLimits() == null) {
            topUpLimitsResponse.setLimits(new TopUpLimitDetailsResponse());
        }

        if (topUpLimitsResponse.getRemaining() == null) {
            topUpLimitsResponse.setRemaining(new TopUpLimitDetailsResponse());
        }

        TopUpLimits topUpLimits = new TopUpLimits();

        topUpLimits.setLimitAmount(topUpLimitsResponse.getLimits().getAmount());
        topUpLimits.setLimitCharges(topUpLimitsResponse.getLimits().getCharges());

        topUpLimits.setRemainingAmount(topUpLimitsResponse.getRemaining().getAmount());
        topUpLimits.setRemainingCharges(topUpLimitsResponse.getRemaining().getCharges());

        return topUpLimits;
    }

    public static TopUpLimitsEntity parseToEntityObject(TopUpLimitsResponse topUpLimitsResponse) {
        if (topUpLimitsResponse == null) {
            topUpLimitsResponse = new TopUpLimitsResponse();
        }

        if (topUpLimitsResponse.getLimits() == null) {
            topUpLimitsResponse.setLimits(new TopUpLimitDetailsResponse());
        }

        if (topUpLimitsResponse.getRemaining() == null) {
            topUpLimitsResponse.setRemaining(new TopUpLimitDetailsResponse());
        }

        TopUpLimitsEntity topUpLimits = new TopUpLimitsEntity();
        topUpLimits.setLimitAmount(topUpLimitsResponse.getLimits().getAmount());
        topUpLimits.setLimitCharges(topUpLimitsResponse.getLimits().getCharges());

        topUpLimits.setRemainingAmount(topUpLimitsResponse.getRemaining().getAmount());
        topUpLimits.setRemainingCharges(topUpLimitsResponse.getRemaining().getCharges());

        return topUpLimits;

    }
    
}
