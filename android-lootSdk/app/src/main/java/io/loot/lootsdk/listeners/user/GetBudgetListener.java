package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.Budget;

public interface GetBudgetListener extends GenericFailListener {

    void onGetCachedBudget(Budget budget);
    void onGetBudgetSuccess(Budget budget);
    void onGetBudgetError(String error);

}
