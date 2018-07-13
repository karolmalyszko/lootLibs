package io.loot.lootsdk.listeners.pushNotifications;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface RegisterNotificationListener extends GenericFailListener {
    void onRegisterNotificationSuccess();
    void onRegisterNotificationError(String error);
}
