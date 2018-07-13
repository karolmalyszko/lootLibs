package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.SavingGoal;

public interface CreateUpdateGoalListener extends GenericFailListener{
    void onCreateModifyGoalSuccess(SavingGoal goal);
    void onCreateModifyGoalError(String error);
}
