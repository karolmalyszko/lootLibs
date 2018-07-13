package io.loot.lootsdk.models.networking.transactions;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.transactions.SpendingDetails;
import lombok.Data;

@Data
public class SpendingDetailsResponse implements Serializable {

    @SerializedName("amount_spent")
    private String amountSpent;

    @SerializedName("number_of_transactions")
    private int transactionsAmount;

    @SerializedName("average_transaction_amount")
    private String averageAmount;


    public static SpendingDetails parseToDataObject(SpendingDetailsResponse spendingDetailsResponse) {
        if (spendingDetailsResponse == null) {
            spendingDetailsResponse = new SpendingDetailsResponse();
        }
        SpendingDetails spendingDets = new SpendingDetails();
        spendingDets.setAmountSpent(spendingDetailsResponse.getAmountSpent());
        spendingDets.setTransactionsAmount(spendingDetailsResponse.getTransactionsAmount());
        spendingDets.setAverageAmount(spendingDetailsResponse.getAverageAmount());
        return spendingDets;
    }

}
