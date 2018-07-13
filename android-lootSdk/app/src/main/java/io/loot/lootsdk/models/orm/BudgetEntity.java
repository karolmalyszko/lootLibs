package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.PrimaryKey;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import io.loot.lootsdk.models.data.Budget;

@android.arch.persistence.room.Entity(tableName = "Budget")
public class BudgetEntity {

    @PrimaryKey
    private long entityId;

    private String dailyBudget;
    private String dailySpent;
    private String leftToSpendToday;
    private float dailyPercentage;
    private String weeklyBudget;
    private String weeklySpent;
    private String leftToSpendThisWeek;
    private float weeklyPercentage;

    public Budget parseToDataObject() {
        Budget budget = new Budget();

        budget.setDailyBudget(dailyBudget);
        budget.setWeeklyBudget(weeklyBudget);
        budget.setDailyPercentage(dailyPercentage);
        budget.setLeftToSpendToday(leftToSpendToday);

        return budget;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getDailyBudget() {
        return this.dailyBudget;
    }

    public void setDailyBudget(String dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    public String getDailySpent() {
        return this.dailySpent;
    }

    public void setDailySpent(String dailySpent) {
        this.dailySpent = dailySpent;
    }

    public String getLeftToSpendToday() {
        return this.leftToSpendToday;
    }

    public void setLeftToSpendToday(String leftToSpendToday) {
        this.leftToSpendToday = leftToSpendToday;
    }

    public float getDailyPercentage() {
        return this.dailyPercentage;
    }

    public void setDailyPercentage(float dailyPercentage) {
        this.dailyPercentage = dailyPercentage;
    }

    public String getWeeklyBudget() {
        return this.weeklyBudget;
    }

    public void setWeeklyBudget(String weeklyBudget) {
        this.weeklyBudget = weeklyBudget;
    }

    public String getWeeklySpent() {
        return this.weeklySpent;
    }

    public void setWeeklySpent(String weeklySpent) {
        this.weeklySpent = weeklySpent;
    }

    public String getLeftToSpendThisWeek() {
        return this.leftToSpendThisWeek;
    }

    public void setLeftToSpendThisWeek(String leftToSpendThisWeek) {
        this.leftToSpendThisWeek = leftToSpendThisWeek;
    }

    public float getWeeklyPercentage() {
        return this.weeklyPercentage;
    }

    public void setWeeklyPercentage(float weeklyPercentage) {
        this.weeklyPercentage = weeklyPercentage;
    }

}
