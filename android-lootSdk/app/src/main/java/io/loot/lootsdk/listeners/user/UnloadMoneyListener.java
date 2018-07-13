package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.SavingGoal;

public interface UnloadMoneyListener extends GenericFailListener {

    void onMoneyUnloaded(SavingGoal loadedGoal);
    void onMoneyUnloadError(String error);

}
