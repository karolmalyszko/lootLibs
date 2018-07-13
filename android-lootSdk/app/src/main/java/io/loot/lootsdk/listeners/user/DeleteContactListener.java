package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface DeleteContactListener extends GenericFailListener {

    void onDeleteContactSuccess();
    void onDeleteContactError(String error);

}
