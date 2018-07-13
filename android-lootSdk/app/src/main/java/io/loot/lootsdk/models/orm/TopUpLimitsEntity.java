package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.loot.lootsdk.models.data.topUp.TopUpLimits;

@Entity(tableName = "TopUpLimits")
public class TopUpLimitsEntity {

    @PrimaryKey
    private long id = 0L;

    private int limitCharges;
    private String limitAmount;
    private int remainingCharges;
    private String remainingAmount;

    public TopUpLimits parseToDataObject() {
        return new TopUpLimits(limitCharges, limitAmount, remainingCharges, remainingAmount);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLimitCharges() {
        return limitCharges;
    }

    public void setLimitCharges(int limitCharges) {
        this.limitCharges = limitCharges;
    }

    public String getLimitAmount() {
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
        return remainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}