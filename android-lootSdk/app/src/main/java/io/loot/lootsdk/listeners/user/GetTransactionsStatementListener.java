package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import okhttp3.ResponseBody;

public interface GetTransactionsStatementListener extends GenericFailListener {
    void onGetTransactionsStatementSuccess(ResponseBody responseBody);
    void onGetTransactionsStatementError(String error);
}
