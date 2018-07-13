package io.loot.lootsdk.listeners.user;

import java.util.ArrayList;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.transactions.Transaction;

public interface GetTransactionsListener extends GenericFailListener{
    void onGetCachedTransactions(ArrayList<Transaction> transactions);
    void onGetTransactionsSuccess(ArrayList<Transaction> transactions);
    void onGetTransactionsError(String error);
}
