package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.transactions.Transaction;

public interface SetTransactionStatusListener extends GenericFailListener{
    void onSetTransactionStatusSuccess(Transaction transaction);
    void onSetTransactionStatusError(String error);
}
