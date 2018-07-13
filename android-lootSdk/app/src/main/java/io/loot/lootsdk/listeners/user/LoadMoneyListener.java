package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.SavingGoal;

public interface LoadMoneyListener extends GenericFailListener {

    void onMoneyLoaded(SavingGoal loadedGoal);
    void onMoneyLoadError(String error);

}
