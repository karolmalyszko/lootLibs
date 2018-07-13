package io.loot.lootsdk.listeners.user;

import java.util.ArrayList;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.SavingGoal;
import io.loot.lootsdk.models.data.contacts.AllContacts;

public interface GetContactsListener extends GenericFailListener {

    void onGetCachedContacts(AllContacts cachedContacts);
    void onGetContactsSuccess(AllContacts contacts);
    void onGetContactsError(String error);

}
