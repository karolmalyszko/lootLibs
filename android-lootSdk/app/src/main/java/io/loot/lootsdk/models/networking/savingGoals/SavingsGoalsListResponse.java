package io.loot.lootsdk.models.networking.savingGoals;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.loot.lootsdk.models.data.SavingGoal;
import io.loot.lootsdk.models.orm.SavingsGoalEntity;
import lombok.Data;

@Data
public class SavingsGoalsListResponse {
    @SerializedName("goals")
    ArrayList<SavingsGoalResponse> goals;

    public static  ArrayList<SavingsGoalEntity> parseToEntityObject(SavingsGoalsListResponse savingsGoalsListResponse) {
        if (savingsGoalsListResponse == null) {
            savingsGoalsListResponse = new SavingsGoalsListResponse();
        }
        if(savingsGoalsListResponse.getGoals() == null) {
            savingsGoalsListResponse.setGoals(new ArrayList<SavingsGoalResponse>());
        }
        ArrayList<SavingsGoalEntity> savingsGoalEntities = new ArrayList<SavingsGoalEntity>();
        for (SavingsGoalResponse savingsGoalResponse : savingsGoalsListResponse.getGoals()) {
            savingsGoalEntities.add(SavingsGoalResponse.parseToEntityObject(savingsGoalResponse));
        }
        return savingsGoalEntities;
    }

    public static ArrayList<SavingGoal>  parseToDataObject(SavingsGoalsListResponse savingsGoalsListResponse) {
        if (savingsGoalsListResponse == null) {
            savingsGoalsListResponse = new SavingsGoalsListResponse();
        }
        if(savingsGoalsListResponse.getGoals() == null) {
            savingsGoalsListResponse.setGoals(new ArrayList<SavingsGoalResponse>());
        }
        ArrayList<SavingGoal> savingGoals = new ArrayList<SavingGoal>();
        for (SavingsGoalResponse savingsGoalResponse : savingsGoalsListResponse.getGoals()) {
            savingGoals.add(SavingsGoalResponse.parseToDataObject(savingsGoalResponse));
        }
        return savingGoals;
    }
}
