package io.loot.lootsdk.models.networking.user;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.Budget;
import io.loot.lootsdk.models.orm.BudgetEntity;
import lombok.Data;

public @Data class BudgetResponse implements Serializable {
    @SerializedName("daily_budget")
    String dailyBudget;

    @SerializedName("daily_spent")
    String dailySpent;

    @SerializedName("left_to_spend_today")
    String leftToSpendToday;

    @SerializedName("daily_percentage")
    float dailyPercentage;

    @SerializedName("weekly_budget")
    String weeklyBudget;

    @SerializedName("weekly_spent")
    String weeklySpent;

    @SerializedName("left_to_spend_this_week")
    String leftToSpendThisWeek;

    @SerializedName("weekly_percentage")
    float weeklyPercentage;

    public static BudgetEntity parseToEntityObject(BudgetResponse budgetResponse) {
        if (budgetResponse == null) {
            budgetResponse = new BudgetResponse();
        }
        BudgetEntity entity = new BudgetEntity();

        entity.setEntityId(0);
        entity.setDailyBudget(budgetResponse.getDailyBudget());
        entity.setLeftToSpendToday(budgetResponse.getLeftToSpendToday());
        entity.setWeeklyBudget(budgetResponse.getWeeklyBudget());
        entity.setDailyPercentage(budgetResponse.getDailyPercentage());
        entity.setDailySpent(budgetResponse.getDailySpent());
        entity.setWeeklySpent(budgetResponse.getWeeklySpent());
        entity.setLeftToSpendThisWeek(budgetResponse.getLeftToSpendThisWeek());

        return entity;
    }

    public static Budget parseToDataObject(BudgetResponse budgetResponse) {
        if (budgetResponse == null) {
            budgetResponse = new BudgetResponse();
        }
        Budget budget = new Budget();

        budget.setDailyBudget(budgetResponse.getDailyBudget());
        budget.setDailyPercentage(budgetResponse.getDailyPercentage());
        budget.setLeftToSpendToday(budgetResponse.getLeftToSpendToday());
        budget.setWeeklyBudget(budgetResponse.getWeeklyBudget());

        return budget;
    }

}
