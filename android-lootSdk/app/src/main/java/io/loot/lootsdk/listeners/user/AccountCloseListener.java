package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface AccountCloseListener extends GenericFailListener {
    void onAccountCloseSuccess();
    void onAccountCloseError(String error);
}
