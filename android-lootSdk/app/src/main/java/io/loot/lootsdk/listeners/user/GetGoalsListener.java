package io.loot.lootsdk.listeners.user;

import java.util.ArrayList;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.SavingGoal;

public interface GetGoalsListener extends GenericFailListener {

    void onGetCachedGoals(ArrayList<SavingGoal> goals);
    void onGetGoalsSuccess(ArrayList<SavingGoal> goals);
    void onGetGoalsError(String error);

}
