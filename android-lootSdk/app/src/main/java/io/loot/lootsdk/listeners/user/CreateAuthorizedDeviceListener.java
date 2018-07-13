package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface CreateAuthorizedDeviceListener extends GenericFailListener {

    void onDeviceAdded(String token);
    void onAddDeviceError(String error);

}
