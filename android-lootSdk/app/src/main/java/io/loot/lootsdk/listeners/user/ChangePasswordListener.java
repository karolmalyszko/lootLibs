package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface ChangePasswordListener extends GenericFailListener {
    void onChangePasswordSuccess();
    void onChangePasswordError(String error);
}
