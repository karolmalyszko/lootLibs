package io.loot.lootsdk.models.data.transactions;

import java.io.Serializable;

public class SpendingDetails implements Serializable {

    private String amountSpent;
    private int transactionsAmount;
    private String averageAmount;

    public SpendingDetails() {
        amountSpent = "";
        transactionsAmount = 0;
        averageAmount = "";
    }

    public SpendingDetails(SpendingDetails spendingDetails) {
        amountSpent = spendingDetails.getAmountSpent();
        transactionsAmount = spendingDetails.getTransactionsAmount();
        averageAmount = spendingDetails.getAverageAmount();
    }

    public String getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(String amountSpent) {
        if (amountSpent == null) {
            amountSpent = "";
        }
        this.amountSpent = amountSpent;
    }

    public int getTransactionsAmount() {
        return transactionsAmount;
    }

    public void setTransactionsAmount(int transactionsAmount) {
        this.transactionsAmount = transactionsAmount;
    }

    public String getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(String averageAmount) {
        if (averageAmount == null) {
            averageAmount = "";
        }
        this.averageAmount = averageAmount;
    }
}
