package io.loot.lootsdk.listeners.user;

import java.util.ArrayList;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.transactions.Transaction;

public interface GetPaginatedTransactionsListener extends GenericFailListener{
    void onGetCachedTransactions(ArrayList<Transaction> transactions, boolean hasNextPage);
    void onGetTransactionsSuccess(ArrayList<Transaction> transactions, boolean hasNextPage);
    void onGetTransactionsError(String error);
}
