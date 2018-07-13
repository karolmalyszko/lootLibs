package io.loot.lootsdk.models.data.transactions;

import java.io.Serializable;

public class Spending implements Serializable {

    private SpendingDetails total;
    private SpendingDetails monthly;

    public Spending() {
        total = new SpendingDetails();
        monthly = new SpendingDetails();
    }

    public Spending(Spending spending) {
        total = new SpendingDetails(spending.getTotal());
        monthly = new SpendingDetails(spending.getMonthly());
    }

    public SpendingDetails getTotal() {
        return total;
    }

    public void setTotal(SpendingDetails total) {
        if (total == null) {
            total = new SpendingDetails();
        }
        this.total = total;
    }

    public SpendingDetails getMonthly() {
        return monthly;
    }

    public void setMonthly(SpendingDetails monthly) {
        if (monthly == null) {
            monthly = new SpendingDetails();
        }
        this.monthly = monthly;
    }
}
