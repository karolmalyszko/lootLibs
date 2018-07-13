package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface CreateUpdateContactListener extends GenericFailListener {

    void onCreateUpdateContactSuccess(String id);
    void onCreateUpdateContactError(String error);

}
