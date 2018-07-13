package io.loot.lootsdk.listeners.sessions;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface AccountStatusListener extends GenericFailListener {

    void onAccountCreated();
    void onAccountSigningUp();
    void onError(String error);

}
