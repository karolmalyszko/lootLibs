package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface DeleteAuthorizedDeviceListener extends GenericFailListener {

    void onDeviceDeleted();
    void onDeletingError(String error);

}
