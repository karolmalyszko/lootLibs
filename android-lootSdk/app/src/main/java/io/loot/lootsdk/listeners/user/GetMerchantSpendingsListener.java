package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.transactions.Spending;

public interface GetMerchantSpendingsListener extends GenericFailListener {
    void onGetMerchantSpendingsSuccess(Spending spending);
    void onGetMerchantSpendingsError(String error);
}
