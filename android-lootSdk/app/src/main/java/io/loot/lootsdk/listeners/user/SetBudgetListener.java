package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.Budget;

public interface SetBudgetListener extends GenericFailListener {

    void onSetBudgetSuccess(Budget budget);
    void onSetBudgetError(String error);

}
