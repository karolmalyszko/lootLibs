package io.loot.lootsdk.models.data.topUp;

public class TopUpLimits {

    private int limitCharges;
    private String limitAmount;
    private int remainingCharges;
    private String remainingAmount;

    public TopUpLimits() {
        this.limitAmount = "";
        this.remainingAmount = "";
    }

    public TopUpLimits(TopUpLimits topUpLimits) {
        this.limitCharges = topUpLimits.getLimitCharges();
        this.limitAmount = topUpLimits.getLimitAmount();
        this.remainingCharges = topUpLimits.getRemainingCharges();
        this.remainingAmount = topUpLimits.getRemainingAmount();
    }

    public TopUpLimits(int limitCharges, String limitAmount, int remainingCharges, String remainingAmount) {
        this.limitCharges = limitCharges;
        this.limitAmount = limitAmount;
        this.remainingCharges = remainingCharges;
        this.remainingAmount = remainingAmount;
    }

    public int getLimitCharges() {
        return limitCharges;
    }

    public void setLimitCharges(int limitCharges) {
        this.limitCharges = limitCharges;
    }

    public String getLimitAmount() {
        if (limitAmount == null) {
            limitAmount = "";
        }

        return limitAmount;
    }

    public void setLimitAmount(String limitAmount) {
        this.limitAmount = limitAmount;
    }

    public int getRemainingCharges() {
        return remainingCharges;
    }

    public void setRemainingCharges(int remainingCharges) {
        this.remainingCharges = remainingCharges;
    }

    public String getRemainingAmount() {
        if (remainingAmount == null) {
            remainingAmount = "";
        }

        return remainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}
