package io.loot.lootsdk.listeners.user;

import java.util.ArrayList;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.contacts.SyncedContactInfo;

public interface GetSyncedPhonebookContactsListener extends GenericFailListener {
    void onSyncedPhonebookContactsSuccess(ArrayList<SyncedContactInfo> syncedContactInfos);
    void onSyncedPhonebookContactsError(String error);
}
