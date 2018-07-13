package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.transactions.Spending;

public interface GetCategorySpendingsListener extends GenericFailListener {
    void onGetCategorySpendingsSuccess(Spending spending);
    void onGetCategorySpendingsError(String error);
}
