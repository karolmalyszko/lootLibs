package io.loot.lootsdk.listeners.sessions;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface LoginListener extends GenericFailListener {

    void onLoginSuccess();
    void onLoginError(String error);

}
