package io.loot.lootsdk.models.networking.transactions;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.transactions.Spending;
import io.loot.lootsdk.models.data.transactions.SpendingDetails;
import lombok.Data;

@Data
public class SpendingResponse implements Serializable {

    @SerializedName("total")
    private SpendingDetailsResponse total;

    @SerializedName("monthly")
    private SpendingDetailsResponse monthly;


    public static Spending parseToDataObject(SpendingResponse spendingResponse) {
        if (spendingResponse == null) {
            spendingResponse = new SpendingResponse();
        }
        Spending spending = new Spending();
        spending.setTotal(SpendingDetailsResponse.parseToDataObject(spendingResponse.getTotal()));
        spending.setMonthly(SpendingDetailsResponse.parseToDataObject(spendingResponse.getMonthly()));
        return spending;
    }

}
