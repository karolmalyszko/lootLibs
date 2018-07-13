package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface DeleteBudgetListener extends GenericFailListener {

    void onDeleteBudgetSuccess();
    void onDeleteBudgetError(String error);

}
