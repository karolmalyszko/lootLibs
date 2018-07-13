package io.loot.lootsdk.listeners;

import java.util.ArrayList;

import io.loot.lootsdk.models.data.contacts.ContactTransactionHistory;
import io.loot.lootsdk.models.data.contacts.SyncedContactInfo;

public interface GetContactTransactionHistoryListener extends GenericFailListener {
    void onGetContactTransactionHistorySuccess(ContactTransactionHistory contactTransactionHistory);
    void onGetCachedContactTransactionHistorySuccess(ContactTransactionHistory contactTransactionHistory);
    void onGetContactTransactionHistoryError(String error);
}
