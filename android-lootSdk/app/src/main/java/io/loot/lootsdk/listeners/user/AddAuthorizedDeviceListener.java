package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface AddAuthorizedDeviceListener extends GenericFailListener {

    void onDeviceAdded();
    void onAddDeviceError(String error);

}
